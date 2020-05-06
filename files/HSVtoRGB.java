import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
public class HSVtoRGB {
int p=0,a;
double c,x,m,rd,gd,bd,r,g,b;
    File f = null;
    int flag=0;
//int rgb[][][]=new int[3][gh][gw];

    public  HSVtoRGB(int[][][] hsv,int gh,int gw,int[][] alpha){
        BufferedImage img = new BufferedImage(gw, gh, BufferedImage.TYPE_INT_ARGB);
        try {
            int[] ra=new int[10];
            int[] ga=new int[10];
            int[] ba=new int[10];
            for (int i = 0; i < gh; i++) {
                for (int j = 0; j < gw; j++) {

                    c = ((double)hsv[2][i][j]/100) * ((double)hsv[1][i][j]/100);
                    x = c * (1 - Math.abs(Math.floorMod(hsv[0][i][j] / 60, 2) - 1));
                    m = ((double)hsv[2][i][j]/100) - c;

                    if ((hsv[0][i][j] >= 0) & (hsv[0][i][j] < 60)) {
                        rd = c;
                        gd = x;
                        bd = 0;
                    } else if ((hsv[0][i][j] >= 60) & (hsv[0][i][j] < 120)) {
                        rd = x;
                        gd = c;
                        bd = 0;
                    } else if ((hsv[0][i][j] >= 120) & (hsv[0][i][j] < 180)) {
                        rd = 0;
                        gd = c;
                        bd = x;
                    } else if ((hsv[0][i][j] >= 180) & (hsv[0][i][j] < 240)) {
                        rd = 0;
                        gd = x;
                        bd = c;
                    } else if ((hsv[0][i][j] >= 240) & (hsv[0][i][j] < 300)) {
                        rd = x;
                        gd = 0;
                        bd = c;
                    } else if ((hsv[0][i][j] >= 300) & (hsv[0][i][j] < 360)) {
                        rd = c;
                        gd = 0;
                        bd = x;
                    } else {
                        System.out.println("hue error");
                    }
                    r = (rd + m) * 255;
                    g = (gd + m) * 255;
                    b = (bd + m) * 255;
                    a = alpha[i][j];
                    if(flag<10)
                    {
                        ra[j]=(int)r;
                        ga[j]=(int)g;
                        ba[j]=(int)b;
                        flag++;
                    }
                    p = (a << 24) | ((int) r << 16) | ((int) g << 8) | (int) b;
                    img.setRGB(i, j, p);
                }
            }

            System.out.println("\nredarrayfrom2");
            for (int m = 0; m < 10; m++) {
                System.out.print(ra[m] + "    ");
            }
            System.out.println("\ngreenarrayfrom2");
            for (int m = 0; m < 10; m++) {
                System.out.print(ga[m] + "    ");
            }
            System.out.println("\nbluearrayfrom2");
            for (int m = 0; m < 10; m++) {
                System.out.print(ba[m] + "    ");
            }
                    try {
                        f = new File("D:/Project/JavaImp/src/hsvtorgb.jpg");
                        ImageIO.write(img, "jpg", f);
                        System.out.println("Writing complete.");
                    }
                    catch(IOException e)
                    {
                        System.out.println("Error: "+e);
                    }
        }
        catch(Exception e){
            System.out.println("    -> Exception Occurred ");
            e.printStackTrace();
        }
    }

}
