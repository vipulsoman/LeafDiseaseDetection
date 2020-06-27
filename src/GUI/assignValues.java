package GUI;

import Classification.KNN;
import Classification.NaiveBayesnew;
import TextureFeatureExtraction.CCM;
import ShapeFeatureExtraction.ShapeFeatures;

import java.util.Spliterator;

public class assignValues {
    public static void assignFeatures() {
        firstPage.test_features[0] = 0;
        firstPage.test_features[1] = ShapeFeatures.length;
        firstPage.test_features[2] = ShapeFeatures.width;
        firstPage.test_features[3] = ShapeFeatures.perimeter;
        firstPage.test_features[4] = ShapeFeatures.pix_area;
        firstPage.test_features[5] = ShapeFeatures.Form_factor;
        firstPage.test_features[6] = ShapeFeatures.Rectang;
        firstPage.test_features[7] = ShapeFeatures.Aspect_Ratio;
        firstPage.test_features[8] = CCM.entropy_m;
        firstPage.test_features[9] = CCM.homogeneity_m;
        firstPage.test_features[10] = CCM.contrast_m;
        firstPage.test_features[11] = CCM.sos_m;
        firstPage.test_features[12] = CCM.asm_m;
        firstPage.test_features[13] = CCM.homogeneity_r;
        firstPage.test_features[14] = CCM.sos_r;
        firstPage.test_features[15] = CCM.entropy_r;
        firstPage.test_features[16] = CCM.contrast_r;
        firstPage.test_features[17] = CCM.asm_r;


        display();
    }
    public static void assignResult() {
        firstPage.KNNResult = KNN.finalResult;
        firstPage.NBResult = NaiveBayesnew.finalResult;
    }
    public static void display() {
        System.out.println();
        System.out.print("{ ");
        for (int i=0;i<=17;i++) {
            System.out.print(firstPage.test_features[i] + ", ");
        }
        System.out.println("}");
        System.out.println();
    }
}

/*
package GUI;

import Classification.KNN;
import Classification.NaiveBayesnew;
import TextureFeatureExtraction.CCM;
import ShapeFeatureExtraction.ShapeFeatures;

import java.util.Spliterator;

public class assignValues {
    public static void assignFeatures() {
        firstPage.test_features[0] = 0;
        firstPage.test_features[1] = ShapeFeatures.length;
        firstPage.test_features[2] = ShapeFeatures.width;
        firstPage.test_features[3] = ShapeFeatures.perimeter;
        //firstPage.test_features[4] = ShapeFeatures.pix_area;
        firstPage.test_features[4] = ShapeFeatures.Form_factor;
        firstPage.test_features[5] = ShapeFeatures.Rectang;
        firstPage.test_features[6] = ShapeFeatures.Aspect_Ratio;
        firstPage.test_features[7] = CCM.entropy_m;
        firstPage.test_features[8] = CCM.homogeneity_m;
        firstPage.test_features[9] = CCM.contrast_m;
        firstPage.test_features[10] = CCM.sos_m;
        firstPage.test_features[11] = CCM.asm_m;
        firstPage.test_features[12] = CCM.homogeneity_r;
        firstPage.test_features[13] = CCM.sos_r;
        firstPage.test_features[14] = CCM.entropy_r;
        firstPage.test_features[15] = CCM.contrast_r;
        firstPage.test_features[16] = CCM.asm_r;

        display();

    }
    public static void assignResult() {
        firstPage.KNNResult = KNN.finalResult;
        firstPage.NBResult = NaiveBayesnew.finalResult;
    }
    public static void display() {
        for (int i=0;i<17;i++) {
            System.out.println();
            System.out.println(firstPage.test_features[i]);
        }
        System.out.println();
    }
}
*/