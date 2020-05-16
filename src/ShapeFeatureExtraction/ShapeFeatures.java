package ShapeFeatureExtraction;

import Misc.Utility;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.LinkedList;
public class ShapeFeatures{

    static int[][] gimg; //globally accessible img
    static int[][] timg1,timg2,timg3,timg4,finalarea;
    static int h,w, p1_j,p2_j,p3_j,p4_j,p1_i,p2_i,p3_i,p4_i,pix_area;
    static double length,width,Aspect_Ratio,Form_factor,Rectang;
    static  Queue<Integer> qx = new LinkedList<>();
    static  Queue<Integer> qy = new LinkedList<>();
    
    public static void findShapeFeatures(BufferedImage bimg,int perimeter)
    {
        JSONObject obj = new JSONObject();
        h=bimg.getHeight();
        w=bimg.getWidth();
        gimg = Utility.GSArray(bimg); //converts bufferedImage to array
        timg1 = new int[h][w];
        timg2 = new int[h][w];
        timg3 = new int[h][w];
        timg4 = new int[h][w];
        finalarea = new int[h][w];


        for (int i=0;i<h;i++) {
            for (int j = 0; j < w; j++) {
                timg1[i][j] = gimg[i][j];
                timg2[i][j] = gimg[i][j];
                timg3[i][j] = gimg[i][j];
                timg4[i][j] = gimg[i][j];
                //finalarea[i][j] = gimg[i][j];
            }
        }

    try {
        findLength();
        findWidth();
        findArea();
        Aspect_Ratio = length / width;
        Form_factor = (4 * Math.PI * pix_area) / Math.pow(perimeter, 2);
        Rectang = (length * width) / pix_area;
    }
    catch(Exception e)
    {
        System.out.println("Following ERROR Occured during shape F cal:\n" + e.getMessage());
    }
        System.out.println("All Shape Features have been extracted");
        obj.put("perimeter",perimeter);
        obj.put("length",length);
        obj.put("width",width);
        obj.put("area",pix_area);
        obj.put("aspectRatio",Aspect_Ratio);
        obj.put("formFactor",Form_factor);
        obj.put("rectangular",Rectang);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter("DataDirectory/ShapeData.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write(obj.toJSONString());
        pw.flush();
        pw.close();
        System.out.println("All Shape Features have been written to file");
    }

    public static void findLength()
    {
        //for uppermost point of the leaf
        OUTER1:for (int i=0;i<h-1;i++)
        {
            for(int j=0;j<w-1;j++)
            {
                if(gimg[i][j]!=0)
                {
                    p1_i=i;
                    p1_j=j;
                    break OUTER1;
                }
            }
        }

        //for lowermost point of the leaf
        OUTER2:for (int i=h-1;i>0;i--)
        {
            for(int j=0;j<w-1;j++)
            {
                if(gimg[i][j]!=0)
                {
                    p2_i=i;
                    p2_j=j;
                    break OUTER2;
                }
            }
        }

        length=Math.sqrt(Math.pow((p1_i-p2_i), 2)+Math.pow((p1_j-p2_j), 2));
    }

    public static void findWidth()
    {
        //for leftmost point of the leaf
        OUTER1:for (int j=0;j<w-1;j++)
        {
            for(int i=0;i<h-1;i++)
            {
                if(gimg[i][j]!=0)
                {
                    p3_i=i;
                    p3_j=j;
                    break OUTER1;
                }
            }
        }

        //for rightmost point of the leaf
        OUTER2:for (int j=w-1;j>0;j--)
        {
            for(int i=0;i<h-1;i++)
            {
                if(gimg[i][j]!=0)
                {
                    p4_i=i;
                    p4_j=j;
                    break OUTER2;
                }
            }
        }

        width=Math.sqrt(Math.pow((p3_i-p4_i), 2)+Math.pow((p3_j-p4_j), 2));
    }

    public static void findArea() throws IOException {
        //flood_fill(p3_i,p1_j);
        drawRays();
        System.out.println("Rays Drawn");


        for (int i=0;i<h-1;i++) {
            for (int j = 0; j < w - 1; j++) {

                if(timg1[i][j]==0 && timg2[i][j]==0 && timg3[i][j]==0 && timg4[i][j]==0)
                {
                    finalarea[i][j]=255;
                }
            }
        }

        for (int i=0;i<h-1;i++)
        {
            for(int j=0;j<w-1;j++)
            {
                if(finalarea[i][j]!=0)
                {
                    pix_area++;
                }
            }
        }

        BufferedImage ti1=Misc.Utility.GSImg(timg1);
        ImageIO.write(ti1, "JPG", new File("images/output/ti1.JPG"));
        BufferedImage ti2=Misc.Utility.GSImg(timg2);
        ImageIO.write(ti2, "JPG", new File("images/output/ti2.JPG"));
        BufferedImage ti3=Misc.Utility.GSImg(timg3);
        ImageIO.write(ti3, "JPG", new File("images/output/ti3.JPG"));
        BufferedImage ti4=Misc.Utility.GSImg(timg4);
        ImageIO.write(ti4, "JPG", new File("images/output/ti4.JPG"));

        BufferedImage areafill=Misc.Utility.GSImg(finalarea);
        ImageIO.write(areafill, "JPG", new File("images/output/8_areafill.JPG"));
    }

    public static void drawRays()
    {
        try {
            //drawing from left to right
            OUTER1:
            for (int i = 0; i < h - 1; i++) {
                for (int j = 0; j < w - 1; j++) {
                    if (timg1[i][j] == 0) {
                        timg1[i][j] = 255;
                    } else
                        continue OUTER1;
                }

            }
            //drawing from right to left
            OUTER2:
            for (int i = 0; i < h - 1; i++) {
                for (int j = w - 1; j > 0; j--) {
                    if (timg2[i][j] == 0) {
                        timg2[i][j] = 255;
                    } else
                        continue OUTER2;
                }
            }

            //drawing from top to bottom
            OUTER3:
            for (int j = 0; j < w - 1; j++) {
                for (int i = 0; i < h - 1; i++) {
                    if (timg3[i][j] == 0) {
                        timg3[i][j] = 255;
                    } else
                        continue OUTER3;
                }
            }

            //drawing from bottom to top
            OUTER4:
            for (int j = 0; j < w - 1; j++) {
                for (int i = h - 2; i > 1; i--) {
                    if (timg4[i][j] == 0) {
                        timg4[i][j] = 255;
                    } else
                        continue OUTER4;
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
/*
    static void flood_fill_iter(int x,int y){
        int x1,y1;
        Integer iox = Integer.valueOf(x);
        Integer ioy = Integer.valueOf(y);
        Integer ix,iy;
        qx.add(iox);
        qy.add(ioy);
        while (!qx.isEmpty() && !qy.isEmpty())
        {
            if(qx.size()==qy.size()) {
                ix = qx.remove();
                //ix=Integer.valueOf(x1);
                x1=ix.intValue();
                iy = qy.remove();
                //iy=Integer.valueOf(y1);
                y1=iy.intValue();
                if(timg1[x1][y1]==0){
                    timg1[x1][y1]=255;
                }
                if (timg1[x1 + 1][y1]==0){
                    ix=Integer.valueOf(x1+1);
                    qx.add(ix);
                    iy=Integer.valueOf(y1);
                    qy.add(iy);
                }
                if (timg1[x1 - 1][y1]==0){
                    //qx.add(x1 - 1);
                    //qy.add(y1);
                    ix=Integer.valueOf(x1-1);
                    qx.add(ix);
                    iy=Integer.valueOf(y1);
                    qy.add(iy);
                }
                if (timg1[x1][y1 + 1]==0){
                    //qx.add(x1);
                    //qy.add(y1+1);
                    ix=Integer.valueOf(x1);
                    qx.add(ix);
                    iy=Integer.valueOf(y1+1);
                    qy.add(iy);
                }
                if (timg1[x1][y1 - 1]==0){
                    //qx.add(x1);
                    //qy.add(y1 - 1);
                    ix=Integer.valueOf(x1);
                    qx.add(ix);
                    iy=Integer.valueOf(y1-1);
                    qy.add(iy);
                }
            }
            else
            {
                System.out.println("Queue size different in floodfill");
            }
        }

    }

    static void floodfill_recur(int x, int y, int prevC, int newC)
    {
        try {
        // Base cases 
        //if (x < 0 || x > h-1 || y < 0 || y > w-1)

        //else if (timg[x][y] > prevC)

        //else
            if (timg[x][y] == prevC){
            timg[x][y] = newC;
            floodfill(x+1, y, prevC, newC);
            floodfill(x-1, y, prevC, newC);
            floodfill(x, y+1, prevC, newC);
            floodfill(x, y-1, prevC, newC);
        }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    static void oddevenfill()
    {
        Boolean inside = false;

        ROW:for (int i = 0; i < h; i++) {
            {
                inside=false;
                for (int j = 0; j < w; j++) {
                    if(timg[i][j]==255 && !inside)
                    {
                        if(entrybordercheck(i,j)==false)
                        inside=true;
                    }
                    else
                    if(timg[i][j]== 0 && inside){
                        timg[i][j]=255;
                    }
                    else
                    if(timg[i][j]==255 && inside)
                    {
                        inside=false;
                        continue ROW;
                    }
                }
            }

        }
    }
    static Boolean entrybordercheck(int i,int j){
        int nextXpix = 5;
        Boolean whiteFound=false;
        for (int k = 1; k < nextXpix; k++) {
            if(timg[i][j+k]==255)
                whiteFound=true;
        }
        return whiteFound;
    }

     */
}