package ImgSegmentation;

public class Masking {
    int gh,gw;
    int mask[][], finalImage[][];
        public Masking(RGBtoHSV img){
        //ImgSegmentation.RGBtoHSV img = new ImgSegmentation.RGBtoHSV("C:\\Users\\shubh\\Desktop\\image.jpg");

        gh=img.gh;
        gw=img.gw;

        mask = new int[gw][gh];
        finalImage = new int[gw][gh];

        for (int l = 0; l < gw; l++) {
            for (int m = 0; m < gh; m++) {
                mask[l][m]=img.hsv[0][l][m];
            }
        }

        //System.out.println();
        //System.out.println();

        /*
        System.out.println("Before ImgSegmentation.Masking:");
        for (int l = 0; l < gw; l++) {
            for (int m = 0; m < gh; m++) {
                System.out.print(mask[l][m] + "\t");
            }
            System.out.println("");
        }

         */

        for (int l = 0; l < gw; l++) {
            for (int m = 0; m < gh; m++) {
                if( (mask[l][m] >=30 && mask[l][m] <=160)  || (mask[l][m] >=7 && mask[l][m] <=25) )
                    finalImage[l][m] = 1;
                else
                    finalImage[l][m] = 0;
            }
        }

        //System.out.println();
        //System.out.println();

        /*
        System.out.println("After ImgSegmentation.Masking:");
        for (int l = 0; l < gw; l++) {
            for (int m = 0; m < gh; m++) {
                System.out.print(finalImage[l][m] + "\t");
            }
            System.out.println("");
        }

         */
        System.out.println("\t\t->ImgSegmentation.Masking of the image completed.");
    }
}
