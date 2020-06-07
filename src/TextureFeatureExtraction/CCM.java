package TextureFeatureExtraction;
import Main.ImSeg_SubDriver;
import ShapeFeatureExtraction.ShapeFeatures;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static ShapeFeatureExtraction.ShapeFeatures.Aspect_Ratio;
import static ShapeFeatureExtraction.ShapeFeatures.Form_factor;
@SuppressWarnings("unchecked")

public class CCM {
    int gh=0,gw=0;

    int red[][][], green[][][], blue[][][];
    int total[][][], mean[][], range[][];
    double mean_N[][], range_N[][];

    double contrast_m, asm_m, sos_m, entropy_m, homogeneity_m;
    double contrast_r, asm_r, sos_r, entropy_r, homogeneity_r;

    int[][][] img;
    BufferedImage image = null;

    File myFile = new File("DataDirectory/FeatureData.json");


    public CCM(String filename) {
        File file = new File(filename);

        int i,j;
        try {
            //reading image
            image = ImageIO.read(file);
            int gh=image.getHeight();
            int gw=image.getWidth();

            img = new int[3][gw][gh];

            red = new int[4][256][256];
            green = new int[4][256][256];
            blue = new int[4][256][256];
            total = new int[4][256][256];

            mean = new int[256][256];
            range = new int[256][256];

            //mean_N = new double[256][256];
            //range_N = new double[256][256];


            for(int degree=0;degree<4;degree++) {
                for(i=0;i <256; i++) {
                    for (j=0; j<256; j++) {
                        red[degree][i][j] = 0;
                        green[degree][i][j] = 0;
                        blue[degree][i][j] = 0;
                    }
                }
            }

            System.out.println();
            int p=0,a,r,g,b;
            for(i=0;i <gw; i++) {
                for (j=0; j<gh; j++) {
                    p = image.getRGB(i, j);
                    //System.out.println(i+ " " +j);
                    //getting RGBA components
                    a = (p >> 24) & 0xff;
                    r = (p >> 16) & 0xff;
                    g = (p >> 8) & 0xff;
                    b = p & 0xff;
                    img[0][i][j] = r;
                    img[1][i][j] = g;
                    img[2][i][j] = b;
                }
            }

            //display(img[0]);display(img[1]);display(img[2]);

            calculate_CCM(gh, gw, img);
        }
        catch(IOException e) {
            //System.out.println("\t-> Exception Occurred while reading file");
            e.printStackTrace();
        }
    }

    public void calculate_CCM(int gh,int gw, int img[][][]) {
        int i,j;
        int x,y;   //counts

        //Bidirectional CCM
        //We will get 3 matrices (R, G, B): each has one dimension for the degree(0,45,90,135);
        //and the other two dimensions for the count of color tones (i,j) from 0 to 255
        //value:degree -> 0:0, 1:45, 2:90, 3:135

        //0 degree
        for(i=0;i <gw-1; i++) {         //last column is skipped
            for (j=0; j<gh; j++) {
                x = img[0][i][j];
                y = img[0][i+1][j];
                red[0][x][y]++;
                red[0][y][x]++;

                x = img[1][i][j];
                y = img[1][i+1][j];
                green[0][x][y]++;
                green[0][y][x]++;

                x = img[2][i][j];
                y = img[2][i+1][j];
                blue[0][x][y]++;
                blue[0][y][x]++;
            }
        }

        //45 degree
        for(i=0;i <gw-1; i++) {         //first row and last column are skipped
            for (j=1; j<gh; j++) {
                x = img[0][i][j];
                y = img[0][i+1][j-1];
                red[1][x][y]++;
                red[1][y][x]++;

                x = img[1][i][j];
                y = img[1][i+1][j-1];
                green[1][x][y]++;
                green[1][y][x]++;

                x = img[2][i][j];
                y = img[2][i+1][j-1];
                blue[1][x][y]++;
                blue[1][y][x]++;
            }
        }

        //90 degree
        for(i=0;i <gw; i++) {         //first row is skipped
            for (j=1; j<gh; j++) {
                x = img[0][i][j];
                y = img[0][i][j-1];
                red[2][x][y]++;
                red[2][y][x]++;

                x = img[1][i][j];
                y = img[1][i][j-1];
                green[2][x][y]++;
                green[2][y][x]++;

                x = img[2][i][j];
                y = img[2][i][j-1];
                blue[2][x][y]++;
                blue[2][y][x]++;
            }
        }

        //135 degree
        for(i=1;i <gw; i++) {         //first row and first column are skipped
            for (j=1; j<gh; j++) {
                x = img[0][i][j];
                y = img[0][i-1][j-1];
                red[3][x][y]++;
                red[3][y][x]++;

                x = img[1][i][j];
                y = img[1][i-1][j-1];
                green[3][x][y]++;
                green[3][y][x]++;

                x = img[2][i][j];
                y = img[2][i-1][j-1];
                blue[3][x][y]++;
                blue[3][y][x]++;
            }
        }

        /*

        System.out.println();   System.out.println("RED");
        display(red[0]);    display(red[1]);    display(red[2]);    display(red[3]);
        System.out.println();   System.out.println("GREEN");
        display(green[0]);    display(green[1]);    display(green[2]);    display(green[3]);
        System.out.println();   System.out.println("BLUE");
        display(blue[0]);    display(blue[1]);    display(blue[2]);    display(blue[3]);

        */

        //Calculate mean and range matrix
        int min, max;
        for(i=0; i<256; i++) {
            for (j=0; j<256; j++) {
                total[0][i][j] = red[0][i][j] + green[0][i][j] + blue[0][i][j];     //0 degree
                total[1][i][j] = red[1][i][j] + green[1][i][j] + blue[1][i][j];     //45 degree
                total[2][i][j] = red[2][i][j] + green[2][i][j] + blue[2][i][j];     //90 degree
                total[3][i][j] = red[3][i][j] + green[3][i][j] + blue[3][i][j];     //135 degree

                mean[i][j] =  (total[0][i][j] + total[1][i][j] + total[2][i][j] + total[3][i][j]) / 4;
                min = total[0][i][j]; max = total[0][i][j];
                for(int k=1; k<4; k++) {
                    if(total[k][i][j] < min)  min = total[k][i][j];
                    if(total[k][i][j] > max)  max = total[k][i][j];
                }
                range[i][j] = max - min;
            }
        }

        /*
        System.out.println("Mean");
        display(mean);
        System.out.println("Range");
        display(range);

         */

        mean_N = GetNormalizeMatrix(mean);
        range_N = GetNormalizeMatrix(range);

        /*
        System.out.println("Normalised Mean");
        display(mean_N);
        System.out.println("Normalised Range");
        display(range_N);

         */

        contrast_m = Contrast(mean_N);
        contrast_r = Contrast(range_N);

        asm_m = AngularSecondMoment(mean_N);
        asm_r = AngularSecondMoment(range_N);

        entropy_m = Entropy(mean_N);
        entropy_r = Entropy(range_N);

        homogeneity_m = Homogenity(mean_N);
        homogeneity_r = Homogenity(range_N);

        sos_m = SumOfSquares(mean_N);
        sos_r = SumOfSquares(range_N);

        System.out.println("10 Extracted Features ->");
        System.out.println();
        System.out.println(": Mean :");
        System.out.println("Contrast : " + contrast_m);
        System.out.println("Angular Second Moment : " + asm_m);
        System.out.println("Entropy : " + entropy_m);
        System.out.println("Homogeneity : " + homogeneity_m);
        System.out.println("Sum of Squares : " + sos_m);
        System.out.println();
        System.out.println(": Range :");
        System.out.println("Contrast : " + contrast_r);
        System.out.println("Angular Second Moment : " + asm_r);
        System.out.println("Entropy : " + entropy_r);
        System.out.println("Homogeneity : " + homogeneity_r);
        System.out.println("Sum of Squares : " + sos_r);
        try {
        if(myFile.createNewFile()) {
            PrintWriter outFile = new PrintWriter(new FileWriter("DataDirectory/FeatureData.json"));
            outFile.write("[");
            outFile.write("]");
            outFile.flush();
            outFile.close();
        };
        }catch (IOException e){
                e.printStackTrace();
        }


        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader("DataDirectory/FeatureData.json"));
            JSONArray jsonArray = (JSONArray)obj;
            JSONObject m = new JSONObject();

            m.put("id","bs002");
            m.put("disease","BacterialSpot");
            m.put("perimeter", ShapeFeatures.perimeter);
            m.put("length", ShapeFeatures.length);
            m.put("width", ShapeFeatures.width);
            m.put("area", ShapeFeatures.pix_area);
            m.put("aspectRatio", ShapeFeatures.Aspect_Ratio);
            m.put("formFactor", ShapeFeatures.Form_factor);
            m.put("rectangular", ShapeFeatures.Rectang);
            m.put("mcontrast", contrast_m);
            m.put("masm", asm_m);
            m.put("mentropy", entropy_m);
            m.put("mhomo", homogeneity_m);
            m.put("msos", sos_m);
            m.put("rcontrast", contrast_r);
            m.put("rasm", asm_r);
            m.put("rentropy", entropy_r);
            m.put("rhomo", homogeneity_r);
            m.put("rsos", sos_r);

            jsonArray.add(m);

            FileWriter file = new FileWriter("DataDirectory/FeatureData.json");
            file.write(jsonArray.toJSONString());
            file.flush();
            file.close();
        }
        catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("All Features have been written to json file");
    }//calculate_CCM ends here

    private int FindTotal(int [][] m){
        int temp = 0;
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp += m[i][j];
            }
        }
        return temp;
    }

    private double[][] GetNormalizeMatrix(int [][] m){
        double[][] temp = new double[m.length][m[0].length];
        int total = FindTotal(m);
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = (double) m[i][j] / total;
            }
        }
        return temp;
    }

    private double Contrast(double[][] matrix) {
        double temp = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp += matrix[i][j] * Math.pow(i-j, 2);
            }
        }
        return temp;
    }

    private double AngularSecondMoment(double[][] matrix) {
        double temp = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp += Math.pow(matrix[i][j], 2);
            }
        }
        return temp;
    }

    private double Entropy(double[][] matrix) {
        double temp = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    temp += (matrix[i][j] * Math.log10(matrix[i][j])) * -1;
                }
            }
        }
        return temp;
    }

    private double Homogenity(double[][] matrix) {
        double temp = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp += matrix[i][j] / (1+Math.pow(i-j, 2));
            }
        }
        return temp;
    }


    private double SumOfSquares(double[][] matrix) {
        double temp = 0;

        double mean = 0;
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                mean += matrix[i][j];
            }
        }
        mean = mean / (matrix.length*matrix[0].length);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp += matrix[i][j] * Math.pow(i-mean, 2);
            }
        }
        return temp;
    }

    public void display(int[][] matrix) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void display(double[][] matrix) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

}