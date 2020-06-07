package ImgSegmentation;
import Misc.Utility;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class regionGrowing {
    public static void runner() throws IOException {
        try
        {
            BufferedImage input1 = ImageIO.read(new File("images/input/1_leaf.JPG"));
            BufferedImage output ;
            int h = input1.getHeight();
            int w = input1.getWidth();
            int pr,pg,pb,pa;
            int [][] im =  Utility.GSArray(input1);
            int [][] rarray = new int[h][w];
            int [][] garray = new int[h][w];
            int [][] barray = new int[h][w];
            int [][] aarray = new int[h][w];
            int [][] mask =new int[h][w];
            int [][] finali =new int[h][w];
            int [][] bdiff =new int[h][w];
            int [][] adiff =new int[h][w];
            pr = rarray[0][0];
            pg = garray[0][0];
            pb = barray[0][0];
            pa = aarray[0][0];

            output = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);

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
            int a = 0, r = 0, g= 0, b = 0;
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    // diff
                    if (Math.abs(pr - rarray[i][j]) >= 10 && Math.abs(pg - garray[i][j]) >= 10 && Math.abs(pb - barray[i][j]) >= 10) {
                        mask[i][j] = 1;
                    } else {
                        mask[i][j] = 0;
                    }
                    if (mask[i][j] == 1) {
                        p = input1.getRGB(i, j);
                        output.setRGB(i, j, p);
                    } else {
                        p = (a << 24) | (r << 16) | (g << 8) | b;
                        output.setRGB(i, j, p);
                    }
                }
            }
            System.out.println("Ol=kay");
            //File f = new File("OutputSegment.png");
            ImageIO.write(output,"PNG",  new File("images/output/opseg.png"));
            System.out.println("Done kay");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
