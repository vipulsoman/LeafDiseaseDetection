package ImgSegmentation;

import Training.Driver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class removeNoise {
    int gh=0,gw=0;
    int[][][] img;
    private static BufferedImage original = null;
    public static BufferedImage outputImage = null;

    public removeNoise() {
        try {
            //reading image
            File file = new File(Driver.inputfpath);
            original = ImageIO.read(file);
            gh=original.getHeight();
            gw=original.getWidth();

            outputImage = new BufferedImage(gw, gh, BufferedImage.TYPE_INT_ARGB);
            img = new int[3][gw][gh];
            System.out.println();
            int p=0,i,j;
            for (i=0; i<gw; i++) {
                for(j=0;j<gh;j++) {
                    p = original.getRGB(i, j);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    img[0][i][j] = r;
                    img[1][i][j] = g;
                    img[2][i][j] = b;
                }
            }
            median(img,gh,gw);
        }
        catch(IOException e) {
            System.out.println("-> Exception Occurred while reading file");
            e.printStackTrace();
        }
    }

    public static void median(int img[][][],int hsize, int wsize) {
        int i,j,k,l,m,n,flag=0,temp;
        int rgb;
        int values[] = new int[9];
        int hnewsize=hsize+4;
        int wnewsize=wsize+4;

        int image[][][] = new int[3][wnewsize][hnewsize];
        int imageout[][][] = new int[3][wnewsize][hnewsize];
        int finalimage[][][] = new int[3][wnewsize][hsize];

        //System.out.println("Image before Median Filtering :");
        //display(img,hsize,wsize);

        try {
            for(rgb=0;rgb<3;rgb++)
                for(i=0;i<wsize;i++)
                    for(j=0;j<hsize;j++)
                        image[rgb][i+2][j+2]=img[rgb][i][j];

            for(rgb=0;rgb<3;rgb++)
                for(i=2;i<wnewsize-2;i++) {
                    for(j=2;j<hnewsize-2;j++) {
                        for(k=i-1;k<i+2;k++) {
                            for(l=j-1;l<j+2;l++) {
                                values[flag]=image[rgb][k][l];
                                flag++;
                            }
                        }
                        // sort the array
                        for(m=0;m<flag;m++) {
                            for(n=m+1;n<flag;n++) {
                                if(values[m]>=values[n]) {
                                    temp=values[n];
                                    values[n]=values[m];
                                    values[m]=temp;
                                }
                            }
                        }
                        flag=0;
                        imageout[rgb][i][j]=values[4];
                    }
                }

            for(rgb=0;rgb<3;rgb++)
                for(i=0;i<wsize;i++)
                    for(j=0;j<hsize;j++)
                        finalimage[rgb][i][j] = imageout[rgb][i+2][j+2];


            int p,a,r,g,b;

            for(i=0;i<wsize;i++)
                for(j=0;j<hsize;j++) {
                    p = original.getRGB(i, j);
                    a = (p >> 24) & 0xff;

                    r = finalimage[0][i][j];
                    g = finalimage[1][i][j];
                    b = finalimage[2][i][j];
                    p = (a<<24) | (r<<16) | (g<<8) | b;
                    outputImage.setRGB(i,j,p);
                }

            File f = new File(Driver.outputpath+"1_MedianOutputRGB.png");
            ImageIO.write(outputImage, "png", f);

        }
        catch(IOException e)
        {
            System.out.println("Error: "+e);
        }
    }

    public static void display(int image[][][],int hsize,int wsize) {
        System.out.println();
        for(int i=0;i<wsize;i++) {
            for(int j=0;j<hsize;j++) {
                System.out.print(image[0][i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        for(int i=0;i<wsize;i++) {
            for(int j=0;j<hsize;j++) {
                System.out.print(image[1][i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        for(int i=0;i<wsize;i++) {
            for(int j=0;j<hsize;j++) {
                System.out.print(image[2][i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}