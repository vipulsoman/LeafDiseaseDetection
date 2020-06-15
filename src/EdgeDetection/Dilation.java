package EdgeDetection;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Dilation {
    

    public static int[][] binaryImage(int[][] im, boolean dilateBackgroundPixel){
        
        int width = im[0].length;
        int height = im.length;
        //BufferedImage img2;
        //int[][] im=Tools.Utility.GSArray(img);

        int output[][] = new int[height][width];
        
        //targetValue = 0 for BLACK
        //targetValue = 255;  for WHITE pixels
        
        int targetValue = (dilateBackgroundPixel == true)?0:255;
        
        int reverseValue = (targetValue == 255)?0:255;
        
        //perform dilation
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(im[x][y] == targetValue)
                {
                    /**
                     * We are using a 2x2 kernel
                     * [1, 1
                     *  1, 1]
                     */
                    
                    boolean flag = false;   //this will be set if a pixel of reverse value is found in the mask
                    
                    for(int ty = y; ty <= y + 1 && flag == false; ty++)
                    {
                        for(int tx = x ; tx <= x + 1 && flag == false; tx++)
                        {
                            if(ty >= 0 && ty < height && tx >= 0 && tx < width)
                            {
                                //origin of the mask is on the image pixels
                                if(im[tx][ty] != targetValue)
                                {
                                    flag = true;
                                    output[x][y] = reverseValue;
                                }
                            }
                        }
                    }
                    if(flag == false)
                    {
                        //all pixels inside the mask [i.e., kernel] were of targetValue
                        output[x][y] = targetValue;
                    }
                }
                else
                {
                    output[x][y] = reverseValue;
                }
            }
        }
        //img2=Tools.Utility.GSImg(output);
        return output;
    }
}
