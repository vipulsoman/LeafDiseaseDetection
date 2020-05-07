package ShapeFeatureExtraction;

import Misc.Utility;

import java.awt.image.BufferedImage;

public class BorderTracer {
static int pixcount=0;
    public static BufferedImage trace(BufferedImage img) {
       
        int h=img.getHeight();
        int w=img.getWidth();
        int i=0,j=0,dir=7,ti,tj,si,sj,count=0,i1=0,j1=0;

        int[][] traw= Utility.GSArray(img);
        int [][] bimg=new int[traw.length+80][traw[0].length+80];
        for(int x=20,a=0;a<traw.length;a++,x++)
        for(int y=20,b=0;b<traw[0].length;b++,y++)
        bimg[x][y]=traw[a][b];

        h=h+80;
        w=w+80;
        //int[][] bimg=Tools.Utility.GSArray(img);
        int[][] output =new int[h][w];
         //finding first pixel
        
        OUTER:for(int x=0;x<h;x++)
        {
            INNER:for(int y=0;y<w;y++)
            {
                if(bimg[x][y]!=0)
                {
                    i=x;j=y;
                    break OUTER;
                }
                
            }
        }
        System.out.println("First Pixel found at ("+ i +" , "+j+")");
        System.out.println("Tracing Border");
        si=i;sj=j;
        //loop here
        do
        {
            //System.out.println("Iteration "+ count);
            if(dir%2==0)
                dir=(dir+7)%8;
            else
                dir=(dir+6)%8;

            switch(dir){
                case 0:
                i1=i;
                j1=j+1;
                break;
                case 1:
                i1=i-1;
                j1=j+1;
                break;
                case 2:
                i1=i-1;
                j1=j;
                break;
                case 3:
                i1=i-1;
                j1=j-1;
                break;
                case 4:
                i1=i;
                j1=j-1;
                break;
                case 5:
                i1=i+1;
                j1=j-1;
                break;
                case 6:
                i1=i+1;
                j1=j;
                break;
                case 7:
                i1=i+1;
                j1=j+1;
                break;
                default:System.out.println(" ! Direction Error Occured ! ");
            }

            if(bimg[i1][j1] != 0)
            {
                i=i1;
                j=j1;
                //if(bimg[i][j]>=200) {
                    output[i][j] = 255;

                //}
            }
            else
            {
                ti=i1; tj=j1;//init to the index of zero value found
                //radial traversal
                /**
                 * region clasification as per which i j are manipulated
                 * _______
                 * |1|4|4|
                 * |1| |3|
                 * |2|2|3|
                 * -------
                 */
                while(bimg[ti][tj]==0)
                {
                    if(( ti == i-1 && tj == j-1 && (dir=3)!=0 )|| ( ti == i && tj == j-1  && (dir=4)!=0)) ti++;//region 1
                    else
                    if(( ti == i+1 && tj == j-1  && (dir=5)!=0) || ( ti == i+1 && tj == j  && (dir=6)!=0)) tj++;//r2
                    else 
                    if(( ti == i+1 && tj == j+1  && (dir=7)!=0) || ( ti == i && tj == j+1  && (dir=0)!=1)) ti--;//r3
                    else 
                    if(( ti == i-1 && tj == j+1  && (dir=1)!=0) || ( ti == i-1 && tj == j  && (dir=2)!=0)) tj--;//r4
                    else
                    System.out.println(" ! Region Error Occured ! ");
                }

                i=ti;
                j=tj;
                //if(bimg[i][j]>=200) {
                    output[i][j] = 255;

                //}
            }
            count++;
        }
        while((i!=si && j!=sj)||( count < 65536));//ideally start end pixel should be same but need to find another better way to end the loop temeporaly counting till 100 to end


        //display

        for(int l=0;l<h;l++)
        {
            for(int m=0;m<w;m++)
                if(output[l][m]==255) pixcount++;

        }


        BufferedImage oimg = Utility.GSImg(output);
        System.out.println("Border has been traced");
        return oimg;

    }

    public static int getPerimeter(){
        return pixcount;
    }
}