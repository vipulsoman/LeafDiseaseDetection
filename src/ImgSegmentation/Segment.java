package ImgSegmentation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Segment {
    File f;
    public Segment(Opening obj, String filename) {
        File file = new File(filename);

        BufferedImage image = null;
        BufferedImage outputImage = null;

        try {
            //reading image
            image = ImageIO.read(file);
            int gh=image.getHeight();
            int gw=image.getWidth();
            outputImage = new BufferedImage(gw, gh, BufferedImage.TYPE_INT_ARGB);

            System.out.println();
            int p=0,a,r,g,b;
            for(int i=0;i <gw; i++) {
                for (int j=0; j<gh; j++) {
                    p = image.getRGB(i, j);
                    if(obj.finalImage[i][j]==0) {
                        p = 0;
                        a = 255;
                        r = 0;
                        g = 0;
                        b = 0;
                        p = (a<<24) | (r<<16) | (g<<8) | b;
                        outputImage.setRGB(i,j,p);
                    }
                    else
                        outputImage.setRGB(i,j,p);
                }
            }

            f = new File("images/output/2_SegmentOutput.jpg");
            ImageIO.write(outputImage, "jpg", f); //cant write the file in JPG here for some reason Debug this
            System.out.println("\t\tWriting completed -> Segmented Image successfully wrote");

        }
        catch(IOException e) {
            //System.out.println("\t-> Exception Occurred while reading file");
            e.printStackTrace();
        }
    }
}
