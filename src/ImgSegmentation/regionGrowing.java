package ImgSegmentation;
import Misc.Utility;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class regionGrowing {
    public static void runner(String inputpath) throws IOException {
        try
        {
            BufferedImage input1 = ImageIO.read(new File(inputpath));
            final int threshold = 25;
            int h = input1.getHeight();
            int w = input1.getWidth();
            BufferedImage output = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);
            int pr,pg,pb,pa;
            int [][] rarray = new int[h][w];
            int [][] garray = new int[h][w];
            int [][] barray = new int[h][w];
            int [][] aarray = new int[h][w];
            int [][] mask =new int[h][w];


            int p=0;
            for( int i = 0 ; i < w ; i++ ) {
                for ( int j = 0 ; j < h ; j++ ) {
                    p = input1.getRGB(i, j);
                    //getting RGBA components
                    aarray[i][j] = (p >> 24) & 0xff;
                    rarray[i][j] = (p >> 16) & 0xff;
                    garray[i][j] = (p >> 8) & 0xff;
                    barray[i][j] = p & 0xff;
                }
            }

            pr = rarray[0][0];
            pg = garray[0][0];
            pb = barray[0][0];
            pa = aarray[0][0];

            int a = 0, r = 0, g= 0, b = 0;
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    // diff
                    if ((Math.abs(pr - rarray[i][j]) >= threshold) && (Math.abs(pg - garray[i][j]) >= threshold) && (Math.abs(pb - barray[i][j]) >= threshold)) {

                        p = (a << 24) | (r << 16) | (g << 8) | b;
                        output.setRGB(i, j, p);
                    }
                    else {
                        p = input1.getRGB(i, j);
                        output.setRGB(i, j, p);
                    }
                }
            }
try {
    ImageIO.write(output, "png", new File("images/output/2_SegOP.png"));
    System.out.println("Done with region growing");
} catch (IOException e) {
    e.printStackTrace();
}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
