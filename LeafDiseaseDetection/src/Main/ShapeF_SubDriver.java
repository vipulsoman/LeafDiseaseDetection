package Main;

import ShapeFeatureExtraction.BorderTracer;
import ShapeFeatureExtraction.ShapeFeatures;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ShapeF_SubDriver {
    static int perimeter;
    public static void runner() {
        try {
            BufferedImage input1 = ImageIO.read(new File("images/output/6_BTC.JPG"));
            BufferedImage output1 = BorderTracer.trace(input1);
            perimeter=BorderTracer.getPerimeter();
            ImageIO.write(output1, "JPG", new File("images/output/7_bordertrace.JPG"));

            ShapeFeatures.findShapeFeatures(output1,perimeter);
        }
        catch(Exception ex)
        {
            System.out.println("Following ERROR Occured:\n" + ex.getMessage());
        }
    }
}
