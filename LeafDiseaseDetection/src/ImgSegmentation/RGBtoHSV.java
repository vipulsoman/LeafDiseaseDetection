package ImgSegmentation;

import java.io.File;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class RGBtoHSV{
    int gh=0,gw=0;
    int[][][] hsv;      //hsv[0]=hue;[1]=sat;[2]=value;
    public RGBtoHSV() {}
    public RGBtoHSV(String filename) {
        File file = new File(filename);
        BufferedImage image = null;

        try {
            //reading image
            image = ImageIO.read(file);
            System.out.println("\t\tReading completed -> Original Image successfully read");
            gh=image.getHeight();
            gw=image.getWidth();
            hsv = new int[3][gw][gh];
            //System.out.println();
            int p=0;
            for(int i=0;i<gw;i++) {
                for (int j = 0; j < gh; j++) {
                    p = image.getRGB(i, j);
                    //getting RGBA components
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    toHSV(r, g, b, i, j);
                }
            }
            System.out.println("\t\t->Converted from RGB to HSV");
            /*
            System.out.println("Hue:");
            for (int l = 0; l < gw; l++) {
                for (int m = 0; m < gh; m++) {
                    System.out.print(hsv[0][l][m] + "\t");
                }
                System.out.println("");
            }
            */
        }
        catch(IOException e) {
            System.out.println("\t\t->Exception Occurred while reading file");
            e.printStackTrace();
        }
    }

    public void toHSV(int r,int g,int b,int i,int j){

        double rd=r/255.0,gd=g/255.0,bd=b/255.0;
        double cmax=(rd>gd)?(rd>bd?rd:bd):(gd>bd?gd:bd);
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
}