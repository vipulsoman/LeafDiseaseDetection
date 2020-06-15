

//not used

package ImgSegmentation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class regionGrowingx {
    public static void runner(String pathname) throws IOException {
        try
        {
            //BufferedImage input1 = ImageIO.read(new File("C:\\Users\\shubh\\Desktop\\Leaf (4).jpg"));
            removeNoise obj = new removeNoise();
            BufferedImage input1 = obj.outputImage;
            BufferedImage output ;
            int h = input1.getHeight();
            int w = input1.getWidth();
            int pr,pg,pb,pa;
            int [][] rarray = new int[h][w];
            int [][] garray = new int[h][w];
            int [][] barray = new int[h][w];
            int [][] aarray = new int[h][w];
            int [][] mask =new int[h][w];


            output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

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
            int threshold = 25;

            //(0,0)
            pr = rarray[10][10];
            pg = garray[10][10];
            pb = barray[10][10];
            pa = aarray[10][10];

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    // diff
                    if (Math.abs(pr - rarray[i][j]) >= threshold || Math.abs(pg - garray[i][j]) >= threshold || Math.abs(pb - barray[i][j]) >= threshold) {
                        //System.out.print("r : " + rarray[i][j] );
                        //System.out.print("\tg : " + garray[i][j] );
                        //System.out.println("\tb : " + barray[i][j] );
                        mask[i][j] = 1;
                    } else {
                        mask[i][j] = 0;
                    }
                }
            }

            //(w,0)
            pr = rarray[10][h-10];
            pg = garray[10][h-10];
            pb = barray[10][h-10];
            pa = aarray[10][h-10];

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    // diff
                    if (Math.abs(pr - rarray[i][j]) >= threshold || Math.abs(pg - garray[i][j]) >= threshold || Math.abs(pb - barray[i][j]) >= threshold) {
                        mask[i][j] = mask[i][j];
                    } else {
                        mask[i][j] = 0;
                    }
                }
            }

            //(0,h)
            pr = rarray[w-10][10];
            pg = garray[w-10][10];
            pb = barray[w-10][10];
            pa = aarray[w-10][10];

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    // diff
                    if (Math.abs(pr - rarray[i][j]) >= threshold || Math.abs(pg - garray[i][j]) >= threshold || Math.abs(pb - barray[i][j]) >= threshold) {
                        mask[i][j] = mask[i][j];
                    } else {
                        mask[i][j] = 0;
                    }
                }
            }
            //(w,h)
            pr = rarray[w-10][h-10];
            pg = garray[w-10][h-10];
            pb = barray[w-10][h-10];
            pa = aarray[w-10][h-10];

            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    // diff
                    if (Math.abs(pr - rarray[i][j]) >= threshold || Math.abs(pg - garray[i][j]) >= threshold || Math.abs(pb - barray[i][j]) >= threshold) {
                        mask[i][j] = mask[i][j];
                    } else {
                        mask[i][j] = 0;
                    }
                }
            }


            //writing to a file
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    if (mask[i][j] == 1) {
                        p = input1.getRGB(i, j);
                        output.setRGB(i, j, p);
                    } else {
                        p = (a << 24) | (r << 16) | (g << 8) | b;
                        output.setRGB(i, j, p);
                    }
                }
            }

            //System.out.println("pr : " + pr);
            //System.out.println("pg : " + pg);
            //System.out.println("pb : " + pb);
            System.out.println("Begin");
            //File f = new File("OutputSegment.png");
            ImageIO.write(output,"PNG",  new File("images/output/2_SegOP.png"));
            System.out.println("Done");

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
