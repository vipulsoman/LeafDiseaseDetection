import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class RGBtoHSV{
    int gh=0,gw=0;
    int[][][] hsv;
    int[][] alpha;
    //hsv[0]=hue;[1]=sat;[2]=value;

    public RGBtoHSV(String filename) {
        File file = new File(filename);
        BufferedImage image = null;

        try {
            //reading image
            image = ImageIO.read(file);
            gh=image.getHeight();
            gw=image.getWidth();
            hsv = new int[3][gh][gw];
            alpha=new int[gh][gw];
            System.out.println();
            int p=0,f=0;
            int[] ra=new int[10];
            int[] ga=new int[10];
            int[] ba=new int[10];
            for(int i=0;i<image.getHeight();i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    p = image.getRGB(i, j);
                    //getting RGBA components
                    int a = (p >> 24) & 0xff;
                    alpha[i][j]=a;
                    // get red
                    int r = (p >> 16) & 0xff;
                    // get green
                    int g = (p >> 8) & 0xff;
                    // get blue
                    int b = p & 0xff;
                    if(f<10)
                    {
                        ra[j]=r;
                        ga[j]=g;
                        ba[j]=b;
                        f++;
                    }

                    toHSV(r, g, b, i, j);
                }
            }

                    System.out.println("Hue:");
                    for (int l = 0; l < 10; l++) {
                        for (int m = 0; m < 10; m++) {
                            System.out.print(hsv[0][l][m] + "    ");
                        }
                        System.out.println("");
                    }

                    System.out.println("Saturation:");
                    for (int l = 0; l < 10; l++) {
                        for (int m = 0; m < 10; m++) {
                            System.out.print(hsv[1][l][m] + "    ");
                        }
                        System.out.println("");
                    }
                    System.out.println("Value:");
                    for (int l = 0; l < 10; l++) {
                        for (int m = 0; m < 10; m++) {
                            System.out.print(hsv[2][l][m] + "    ");
                        }
                        System.out.println("");
                    }
            System.out.println("redarray");
            for (int m = 0; m < 10; m++) {
                System.out.print(ra[m] + "    ");
            }
            System.out.println("greenarray");
            for (int m = 0; m < 10; m++) {
                System.out.print(ga[m] + "    ");
            }
            System.out.println("bluearray");
            for (int m = 0; m < 10; m++) {
                System.out.print(ba[m] + "    ");
            }

        }
        catch(IOException e) {
            System.out.println("    -> Exception Occurred while reading file");
            e.printStackTrace();
        }
    }

    public void toHSV(int r,int g,int b,int i,int j)
    {
        double rd=r/255.0,gd=g/255.0,bd=b/255.0;
        double cmax=(rd>gd)?(rd>bd?rd:bd):(gd>bd?gd:bd); //z=a?b:c
        double cmin=(rd<gd)?(rd<bd?rd:bd):(gd<bd?gd:bd);
        double diff=cmax-cmin;
        //hue calculation
        if(cmax==0 && cmin==0){
            hsv[0][i][j]=0;
        }
        else if(cmax==rd){
            hsv[0][i][j]=(int)(60*((gd-bd)/diff) + 360) % 360;
        }
        else if(cmax==gd){
            hsv[0][i][j]=(int)(60*((bd-rd)/diff) + 120) % 360;
        }
        else if(cmax==bd){
            hsv[0][i][j]=(int)(60*((rd-gd)/diff) + 240) % 360;
        }
        else
            System.out.println("Calculation mistake");

        //Saturation Calculation
        if(cmax==0)
            hsv[1][i][j]=0;
        else
            hsv[1][i][j]=(int)((diff/cmax)*100);

        //value cal
        hsv[2][i][j]=(int)(cmax*100);
    }

    public static void main(String[] args){
        RGBtoHSV img1=new RGBtoHSV("D:/Project/JavaImp/src/image1.jpg");
        HSVtoRGB img2=new HSVtoRGB(img1.hsv,img1.gh,img1.gw,img1.alpha);
    }
}
