package ImgSegmentation;

import Main.Driver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
public class regionGrowing {

    public regionGrowing() throws IOException {
    }

    public static BufferedImage region(File f) throws IOException {
        BufferedImage input1 ;
        BufferedImage output = null;
        try
        {
            input1 = ImageIO.read(f);

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
            int threshold = 15;

            //top to bottom
            for (int i = 0; i < w; i++) {
                pr = rarray[i][0];
                pg = garray[i][0];
                pb = barray[i][0];
                pa = aarray[i][0];
                for (int j = 0; j < h; j++) {
                    if (Math.abs(pr - rarray[i][j]) >= threshold || Math.abs(pg - garray[i][j]) >= threshold || Math.abs(pb - barray[i][j]) >= threshold) {
                        mask[i][j] = 1;
                    } else {
                        mask[i][j] = 0;
                    }
                }
            }

            //bottom to top
            for (int i = 0; i < w; i++) {
                pr = rarray[i][h-1];
                pg = garray[i][h-1];
                pb = barray[i][h-1];
                pa = aarray[i][h-1];
                for (int j = 0; j < h; j++) {
                    if (Math.abs(pr - rarray[i][j]) >= threshold || Math.abs(pg - garray[i][j]) >= threshold || Math.abs(pb - barray[i][j]) >= threshold) {
                        mask[i][j] = mask[i][j];
                    } else {
                        mask[i][j] = 0;
                    }
                }
            }

            //left to right
            for (int j = 0; j < h; j++) {
                pr = rarray[0][j];
                pg = garray[0][j];
                pb = barray[0][j];
                pa = aarray[0][j];
                for (int i = 0; i < w; i++) {
                    if (Math.abs(pr - rarray[i][j]) >= threshold || Math.abs(pg - garray[i][j]) >= threshold || Math.abs(pb - barray[i][j]) >= threshold) {
                        mask[i][j] = mask[i][j];
                    } else {
                        mask[i][j] = 0;
                    }
                }
            }
            //right to left
            for (int j = 0; j < h; j++) {
                pr = rarray[w-1][j];
                pg = garray[w-1][j];
                pb = barray[w-1][j];
                pa = aarray[w-1][j];
                for (int i = 0; i < w; i++) {
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

        }catch(Exception e){
            System.out.println("-> Exception Occurred");
            e.printStackTrace();
        }
        return output;
    }

    public static void runner() throws IOException {
        removeNoise med = new removeNoise();// writes 1_MedianOutputRGB.png
        File image1 = new File(Driver.inputfpath);
        File image2 = new File(  Driver.outputpath+"1_MedianOutputRGB.png");
        BufferedImage img1 = region(image1);
        BufferedImage img2 = region(image2);
        ImageIO.write(img2,"PNG",  new File(Driver.outputpath+"2_SegmentWF.png"));
        ImageIO.write(img1,"PNG",  new File(Driver.outputpath+"3_SegmentWOF.png"));

    }
}
