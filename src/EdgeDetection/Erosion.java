package EdgeDetection;

import Misc.Utility;

import java.awt.image.BufferedImage;

public class Erosion {

    public static BufferedImage binaryImage(BufferedImage img, boolean erodeForegroundPixel){

        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage img2;
        int[][] im= Utility.GSArray(img);

        int output[][] = new int[height][width];

        int targetValue = (erodeForegroundPixel == true)?0:255;

        int reverseValue = (targetValue == 255)?0:255;
        
        //perform erosion
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(im[x][y] == targetValue){
                    /**
                     * 2x2 kernel
                     * [1, 1
                     *  1, 1]
                     */
                    boolean flag = false;   //this will be set if a pixel of reverse value is found in the mask
                    for(int ty = y ; ty <= y + 1 && flag == false; ty++){
                        for(int tx = x ; tx <= x + 1 && flag == false; tx++){
                            if(ty >= 0 && ty < height && tx >= 0 && tx < width){
                                //origin of the mask is on the image pixels
                                if(im[tx][ty] != targetValue){
                                    flag = true;
                                    output[x][y] = reverseValue;
                                }
                            }
                        }
                    }
                    if(flag == false){
                        //all pixels inside the mask [i.e., kernel] were of targetValue
                        output[x][y] = targetValue;
                    }
                }else{
                    output[x][y] = reverseValue;
                }
            }
        }

        img2= Utility.GSImg(output);
        return img2;
        
        /**
         * Save the erosion value in image img.
         
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int v = output[x+y*width];
                img.setPixel(x, y, 255, v, v, v);
            }
        }
        */
    }
    
    
}//class ends here
