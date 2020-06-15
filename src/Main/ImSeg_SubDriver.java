package Main;

import ImgSegmentation.*;

import java.io.IOException;

public class ImSeg_SubDriver {
    public static void runner() throws IOException {
        /*
        RGBtoHSV img = new RGBtoHSV(file);
        Masking mask = new Masking(img);
        Opening open = new Opening(mask);
        Segment seg = new Segment(open,file);
        //MedianFilter obj = new MedianFilter(seg);
*/
        regionGrowing.runner();

    }
}