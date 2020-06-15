package Main;

import EdgeDetection.Canny;
import EdgeDetection.Dilation;
import EdgeDetection.Erosion;
import EdgeDetection.Gaussian;
import Misc.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class EdgeD_SubDriver {
    //EdgeDetection.Canny parameters
    private static final double CANNY_THRESHOLD_RATIO = .4; //range .2 - .4   higher more large details
    private static final int CANNY_STD_DEV = 1;             //Range 1-3  lesser number more small details

    public static void runner() {
        int[][] im,im2=null;

        try {
            BufferedImage input1 = ImageIO.read(new File(Driver.outputpath+"2_SegmentWF.png"));
            //canny
            
            BufferedImage output1 = Canny.CannyEdges(input1, CANNY_STD_DEV, CANNY_THRESHOLD_RATIO);
            ImageIO.write(output1, "JPG", new File(Driver.outputpath+"4_canny_op.JPG"));

            //opening
            BufferedImage Eoutput1 = Erosion.binaryImage(output1, false);
            ImageIO.write(Eoutput1, "JPG", new File(Driver.outputpath+"5_erosion_op.JPG"));
            
            //dilate
            im= Utility.GSArray(Eoutput1);
            im2 = Dilation.binaryImage(im, true);
            BufferedImage Doutput1= Utility.GSImg(im2);
            
            ImageIO.write(Doutput1, "JPG", new File(Driver.outputpath+"6_dilation_op.JPG"));
            
            //BlurThresholdClose
            
            BufferedImage btcop=btc(Doutput1,3);
            ImageIO.write(btcop, "JPG", new File(Driver.outputpath+"7_BTC.JPG"));

        }
        catch(Exception ex)
        {
            System.out.println("ERROR ACCESING IMAGE FILE:\n" + ex.getMessage());
        }
    }

    public static BufferedImage btc(BufferedImage Bimg,int iter)
    {
        int[][] im= Utility.GSArray(Bimg);
        int[][] r1 = null;
        int[][] r2 = null;
        int gr=5;
        double gi=1.5;
       
        r1=im;

        while(iter-->0)
        {   
            
            //blur
            r2= Gaussian.GBlur(r1,gr,gi);
          
            //threshold
            for(int i=0; i<r2.length; i++)
            {
                for(int j=0; j<r2[i].length; j++)
                {
                    //System.out.print(r1[i][j]+"  ");
                    if( r2[i][j] < 100 )
                        r2[i][j]=0;
                    else
                        r2[i][j]=r2[i][j];
                }
                //System.out.println("\n");
            }

            //dilate 2x2
            r1=null;
            r1 = Dilation.binaryImage(r2, true);
            //erosion 2x2
            
            r2=null;
            r2 = Utility.GSArray(Erosion.binaryImage(Utility.GSImg(r1), false));
            r1=null;
            r1=r2;
            r2=null;
             
        }
        BufferedImage eblur = Utility.GSImg(r1);
        return eblur;
        
    } 
}
































































/*
            BufferedImage input2 = ImageIO.read(new File("l2.JPG"));
            BufferedImage output2 = JCanny.CannyEdges(input2, CANNY_STD_DEV, CANNY_THRESHOLD_RATIO);
            ImageIO.write(output2, "JPG", new File("l2Aop.JPG"));

            BufferedImage Doutput2 = EdgeDetection.Dilation.binaryImage(output2, true);
            ImageIO.write(Doutput2, "JPG", new File("l2Dop.JPG"));
            BufferedImage Eoutput2 = EdgeDetection.Erosion.binaryImage(Doutput2, false);
            ImageIO.write(Eoutput2, "JPG", new File("l2Eop.JPG"));

            BufferedImage input3 = ImageIO.read(new File("l3.JPG"));
            BufferedImage output3 = JCanny.CannyEdges(input3, CANNY_STD_DEV, CANNY_THRESHOLD_RATIO);
            ImageIO.write(output3, "JPG", new File("l3Aop.JPG"));

            BufferedImage Doutput3 = EdgeDetection.Dilation.binaryImage(output3, true);
            ImageIO.write(Doutput3, "JPG", new File("l3Dop.JPG"));
            BufferedImage Eoutput3 = EdgeDetection.Erosion.binaryImage(Doutput3, false);
            ImageIO.write(Eoutput3, "JPG", new File("l3Eop.JPG"));

            BufferedImage input4 = ImageIO.read(new File("l4.JPG"));
            BufferedImage output4 = JCanny.CannyEdges(input4, CANNY_STD_DEV, CANNY_THRESHOLD_RATIO);
            ImageIO.write(output4, "JPG", new File("l4Aop.JPG"));

            BufferedImage Doutput4 = EdgeDetection.Dilation.binaryImage(output4, true);
            ImageIO.write(Doutput4, "JPG", new File("l4Dop.JPG"));
            BufferedImage Eoutput4 = EdgeDetection.Erosion.binaryImage(Doutput4, false);
            ImageIO.write(Eoutput4, "JPG", new File("l4Eop.JPG"));

            BufferedImage input5 = ImageIO.read(new File("l5.JPG"));
            BufferedImage output5 = JCanny.CannyEdges(input5, CANNY_STD_DEV, CANNY_THRESHOLD_RATIO);
            ImageIO.write(output5, "JPG", new File("l5Aop.JPG"));

            BufferedImage Doutput5 = EdgeDetection.Dilation.binaryImage(output5, true);
            ImageIO.write(Doutput5, "JPG", new File("l5Dop.JPG"));
            BufferedImage Eoutput5 = EdgeDetection.Erosion.binaryImage(Doutput5, false);
            ImageIO.write(Eoutput5, "JPG", new File("l5Eop.JPG"));
            */