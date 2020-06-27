package EdgeDetection;
import Misc.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static Training.Driver.outputpath;

public class Canny {
    private static final int GAUSSIAN_RADIUS = 5;
    private static final double GAUSSIAN_INTENSITY = 1.5;

    private static int stDev;       //Standard deviation in magnitude of image's pixels
    private static int mean;        //Mean of magnitude in image's pixels
    private static int numDev;      //Number of standard deviations above mean for high threshold
    private static double tHi;      //Hysteresis high threshold; Definitely edge pixels, do not examine
    private static double tLo;      //Hysteresis low threshold; possible edge pixel, examine further.
    private static double tFract;   //Low threshold is this fraction of high threshold
    private static int[][] dir;     //Gradient direction mask. Equals Math.atan2(gy/gx)
    private static int[][] gx;      //Mask resulting from horizontal 3x3 EdgeDetection.Sobel mask
    private static int[][] gy;      //Mask resulting from vertical 3x3 EdgeDetection.Sobel mask
    private static double[][] mag;  //Direction mask. Equals Math.sqrt(gx^2 * gy^2)

    
    public static BufferedImage CannyEdges(BufferedImage img, int numberDeviations, double fract)
    {
        int h=img.getHeight();
        int w=img.getWidth();
        int[][] raw = null;
        int[][] traw = null;
        int[][] r1 =new int[h][w];
        int[][] r2 =new int[h][w];
        int[][] blurred = null;
        BufferedImage edges = null;
        BufferedImage eblur = null;
        //BufferedImage eblur1 = null;
        //BufferedImage eblur2 = null;
        numDev = numberDeviations;
        tFract = fract;
        
        try
        {
           
            //More specific bounds checking later
            if (img != null && numberDeviations > 0 && fract > 0)
            {
                traw = Utility.GSArray(img);
                raw=new int[traw.length+80][traw[0].length+80];
                for(int x=20,a=0;a<traw.length;a++,x++)
                for(int y=20,b=0;b<traw[0].length;b++,y++)
                raw[x][y]=traw[a][b];

                blurred = Gaussian.GBlur(raw, GAUSSIAN_RADIUS, GAUSSIAN_INTENSITY);
                eblur=Utility.GSImg(blurred);
                ImageIO.write(eblur, "JPG", new File(outputpath+"2.5_gaussian_op.JPG"));
                gx = Sobel.Horizontal(blurred);  //Convolved with 3x3 horizontal EdgeDetection.Sobel mask
                gy = Sobel.Vertical(blurred);    //Convolved with 3x3 vertical EdgeDetection.Sobel mask

                Magnitude();    //Find the gradient magnitude at each pixel
                Direction();    //Find the gradient direction at each pixel
                Suppression();  //Using the direction and magnitude images, identify candidate points
                edges = Utility.GSImg(Hysteresis());
            }
        }
         catch (Exception ex)
        {
            System.out.println("Error in EdgeDetection.Canny : " + ex.getMessage());
        }
        return edges;
    }


    
    //method the horizontal and vertical EdgeDetection.Sobel convolutions to create the gradient magnitude image.
  
    private static void Magnitude() {
        double sum = 0;
        double var = 0;
        int height = gx.length;
        int width = gx[0].length;
        double pixelTotal = height * width;
        mag = new double[height][width];

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                mag[r][c] = Math.sqrt(gx[r][c] * gx[r][c] + gy[r][c] * gy[r][c]);

                sum += mag[r][c];
            }
        }

        mean = (int) Math.round(sum / pixelTotal);

        //Get variance
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                double diff = mag[r][c] - mean;

                var += (diff * diff);
            }
        }

        stDev = (int) Math.sqrt(var / pixelTotal);
    }

    
    //method the horizontal and vertical EdgeDetection.Sobel convolutions to create the gradient direction image.

    
     
    private static void Direction() {
        int height = gx.length;
        int width = gx[0].length;
        double piRad = 180 / Math.PI;
        dir = new int[height][width];

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                double angle = Math.atan2(gy[r][c], gx[r][c]) * piRad;    //Convert radians to degrees

                //Check for negative angles
                if (angle < 0) {
                    angle += 360.;
                }

                //Each pixels ACTUAL angle is examined and placed in 1 of four groups (for the four searched 45-degree neighbors)
                //Reorder this for optimization
                if (angle <= 22.5 || (angle >= 157.5 && angle <= 202.5) || angle >= 337.5) {
                    dir[r][c] = 0;      //Check left and right neighbors
                } else if ((angle >= 22.5 && angle <= 67.5) || (angle >= 202.5 && angle <= 247.5)) {
                    dir[r][c] = 45;     //Check diagonal (upper right and lower left) neighbors
                } else if ((angle >= 67.5 && angle <= 112.5) || (angle >= 247.5 && angle <= 292.5)) {
                    dir[r][c] = 90;     //Check top and bottom neighbors
                } else {
                    dir[r][c] = 135;    //Check diagonal (upper left and lower right) neighbors
                }
            }
        }
    }

    
    //method to use gradient direction and magnitude to suppress lesser pixels.
    
    private static void Suppression() {
        int height = mag.length - 1;
        int width = mag[0].length - 1;

        for (int r = 1; r < height; r++) {
            for (int c = 1; c < width; c++) {
                double magnitude = mag[r][c];

                switch (dir[r][c]) {
                    case 0 :
                        if (magnitude < mag[r][c - 1] && magnitude < mag[r][c + 1]) {
                            mag [r - 1][c - 1] = 0;
                        }
                        break;
                    case 45 :
                        if (magnitude < mag[r - 1][c + 1] && magnitude < mag[r + 1][c - 1]) {
                            mag [r - 1][c - 1] = 0;
                        }
                        break;
                    case 90 :
                        if (magnitude < mag[r - 1][c] && magnitude < mag[r + 1][c]) {
                            mag [r - 1][c - 1] = 0;
                        }
                        break;
                    case 135 :
                        if (magnitude < mag[r - 1][c - 1] && magnitude < mag[r + 1][c + 1]) {
                            mag [r - 1][c - 1] = 0;
                        }
                        break;
                }
            }
        }
    }

    
    //method to use an upper and lower threshold to decided which non-suppressed pixels are edges.

    private static int[][] Hysteresis() {
        int height = mag.length - 1;
        int width = mag[0].length - 1;
        int[][] bin = new int[height - 1][width - 1];

        tHi = mean + (numDev * stDev);    //Magnitude greater than or equal to high threshold is an edge pixel
        tLo = tHi * tFract;               //Magnitude less than low threshold not an edge, equal or greater possible edge

        for (int r = 1; r < height; r++) {
            for (int c = 1; c < width; c++) {
                double magnitude = mag[r][c];

                if (magnitude >= tHi) {
                    bin[r - 1][c - 1] = 255;
                } else if (magnitude < tLo) {
                    bin[r - 1][c - 1] = 0;
                } else {    //This could be separate method or lambda
                    boolean connected = false;

                    for (int nr = -1; nr < 2; nr++) {
                        for (int nc = -1; nc < 2; nc++) {
                            if (mag[r + nr][c + nc] >= tHi) {
                                connected = true;
                            }
                        }
                    }

                    bin[r - 1][c - 1] = (connected) ? 255 : 0;
                }
            }
        }

        return bin;
    }
}
