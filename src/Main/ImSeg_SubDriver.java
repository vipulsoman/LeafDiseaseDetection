package Main;

import ImgSegmentation.*;

public class ImSeg_SubDriver {
    public static String file = "images/input/seg2.png";
    public static void runner() {

        RGBtoHSV img = new RGBtoHSV(file);
        Masking mask = new Masking(img);
        Opening open = new Opening(mask);
        Segment seg = new Segment(open,file);
        //MedianFilter obj = new MedianFilter(seg);


    }
}