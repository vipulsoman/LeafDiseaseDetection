package ShapeFeatureExtraction;

import Misc.Utility;
import org.json.simple.JSONObject;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ShapeFeatures{

    static int[][] gimg;
    static int[][] timg;
    static int h,w, p1_j,p2_j,p3_j,p4_j,p1_i,p2_i,p3_i,p4_i,pix_area;
    static double length,width,Aspect_Ratio,Form_factor,Rectang;
    
    public static void findShapeFeatures(BufferedImage bimg,int perimeter)
    {
        JSONObject obj = new JSONObject();
        h=bimg.getHeight();
        w=bimg.getWidth();
        gimg = Utility.GSArray(bimg); //converts bufferedImage to array
    try {
        findLength();
        findWidth();
        //findArea();
        Aspect_Ratio = length / width;
        //Form_factor = (4 * Math.PI * pix_area) / Math.pow(perimeter, 2);
        //Rectang = (length * width) / pix_area;
    }
    catch(Exception e)
    {
        System.out.println("Following ERROR Occured during shape F cal:\n" + e.getMessage());
    }
        System.out.println("All Shape Features have been extracted");
        obj.put("perimeter",perimeter);
        obj.put("length",length);
        obj.put("width",width);
        //obj.put("area",pix_area);
        obj.put("aspectRatio",Aspect_Ratio);
        //obj.put("formFactor",Form_factor);
        //obj.put("rectangular",Rectang);

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

        width=Math.sqrt(Math.pow((p3_i-p3_i), 2)+Math.pow((p4_j-p4_j), 2));
    }

    public static void findArea()
    {
        //do floodfill here at i=h/2 and j=w/2
        timg=gimg;
        floodFill(h/4, w/4, 180, 255);
        

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
    }

    static void floodFill(int x, int y, int prevC, int newC) 
    { 
        // Base cases 
        if (x < 0 || x > h-1 || y < 0 || y > w-1)
            return; 
        if (timg[x][y] > prevC) 
            return; 
    
        // Replace the color at (x, y) 
        timg[x][y] = newC; 
    
        // Recur for north, east, south and west 
        floodFill(x+1, y, prevC, newC); 
        floodFill(x-1, y, prevC, newC); 
        floodFill(x, y+1, prevC, newC); 
        floodFill(x, y-1, prevC, newC); 
    }

}