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

    static int[][] gimg;
    static int[][] timg;
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
        timg = new int[h][w];


        for (int i=0;i<h;i++) {
            for (int j = 0; j < w; j++) {
                timg[i][j] = gimg[i][j];
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

        //do floodfill here at i=h/2 and j=w/2
        flood_fill(p3_i,p1_j);
        

        for (int i=0;i<h-1;i++)
        {
            for(int j=0;j<w-1;j++)
            {
                if(timg[i][j]!=0)
                {
                    pix_area++;
                }
            }
        }

        BufferedImage areafill=Misc.Utility.GSImg(timg);
        ImageIO.write(areafill, "JPG", new File("images/output/8_areafill.JPG"));
    }

    static void flood_fill(int x,int y){
        int x1,y1;
        qx.add(x);
        qy.add(y);
        while (!qx.isEmpty() && !qy.isEmpty())
        {
            if(qx.size()==qy.size()) {
                x1 = qx.remove();
                y1 = qy.remove();
                if(timg[x1][y1]==0){
                    timg[x1][y1]=255;
                }
                if (timg[x1 + 1][y1]==0){
                    qx.add(x1 + 1);
                    qy.add(y1);
                }
                if (timg[x1 - 1][y1]==0){
                    qx.add(x1 - 1);
                    qy.add(y1);
                }
                if (timg[x1][y1 + 1]==0){
                    qx.add(x1);
                    qy.add(y1+1);
                }
                if (timg[x1][y1 - 1]==0){
                    qx.add(x1);
                    qy.add(y1 - 1);
                }
            }
            else
            {
                System.out.println("Queue size different in floodfill");
            }
        }

    }



    static void floodFill(int x, int y, int prevC, int newC) 
    {
        try {
        // Base cases 
        //if (x < 0 || x > h-1 || y < 0 || y > w-1)

        //else if (timg[x][y] > prevC)

        //else
            if (timg[x][y] == prevC){
            timg[x][y] = newC;
            floodFill(x+1, y, prevC, newC);
            floodFill(x-1, y, prevC, newC);
            floodFill(x, y+1, prevC, newC);
            floodFill(x, y-1, prevC, newC);
        }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}