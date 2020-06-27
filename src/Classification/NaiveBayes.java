/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classification;

//import static classification.knn_2.knn;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Arrays;

import static Classification.DataClass.training_diseases;
import static java.lang.Math.PI;

/**
 *
 * @author Bilwa
 */
public class NaiveBayes {
    public static String finalResult;


    /*   public static double squareRoot(double n) {
       double temp;

       double sr=n/2;

       do {
           temp = sr;
           sr = (temp + (n / temp))/ 2;
       } while ((temp-sr)!= 0);

       return sr;
       }

      public static double exponential(int n, double x)
       {
           // initialize sum of series
           double sum = 1.0f;

           for (int i = n - 1; i > 0; --i )
               sum = 1 + x * sum / i;

           return sum;
       } */
    public static void NaiveBayes(double[][] training_features, String[] training_diseases, double[] test_features, String[] All_diseases) {
        int i, j, m, n, cnt = 0, k, num = 0;
        double add = 0.0, addv = 0.0, prod = 1.0;
        double mean[][] = new double[6][18];
        double var [][] = new double[6][18];
        int record_number[] = new int[60];
        int target_counts[] = new int[6];
        double probabilities[] = new double[6];
        double gaussian[][] = new double[6][18];
        double p[] = new double[6];
        double posterior[] = new double[6];
        double x;
        /*  for(i=0;i<training_features.length;i++){
              System.out.println(training_features[i].length);
          } */
        for (i = 0; i < All_diseases.length; i++) {
            cnt = 0;
            for (j = 0; j < training_diseases.length; j++) {
                if(All_diseases[i].equals(training_diseases[j])) {      //(All_diseases[i] == training_diseases[j])

                    record_number[cnt] = j;

                    cnt++;

                }



            }
            System.out.println("-----------------------------------------------------------");
            //System.out.println(cnt);
            if (cnt > 0) {
                target_counts[i] = cnt;  //storing counts of diseases
                //function to calculate mean
                for (m = 1; m < training_features[0].length; m++) {
                    add = 0.0;
                    for (n = 0; n < cnt; n++) {
                        //  System.out.print(record_number[n]);
                        add += training_features[record_number[n]][m];
                        System.out.print(" " + add);
                    }
                    System.out.println();
                    mean[i][m - 1] = add / cnt;


                    add = 0.0;
                }
                //function to calculate variance
                for (m = 1; m < training_features[0].length; m++) {
                    addv = 0.0;
                    for (n = 0; n < cnt; n++) {
                        addv += ((training_features[record_number[n]][m] - mean[i][m - 1]) * (training_features[record_number[n]][m] - mean[i][m - 1]));
                    }

                    var [i][m-1]=addv / cnt;


                    addv = 0.0;
                }



            }
            else {
                continue;
            }
        }

        //print mean and variance
        for (i = 0; i < All_diseases.length; i++) {
            for (j = 0; j < training_features[0].length - 1; j++) {
                System.out.print(" m: " + mean[i][j] + " v: " +var [i][j]);
            }

            System.out.println();
        }

        //calculate probabilities for every disease
        for (i = 0; i < All_diseases.length; i++) {

            probabilities[i] = (double)target_counts[i] / (double)training_features.length;

        }
        //print the probabilities
        for (i = 0; i < All_diseases.length; i++) {

            System.out.print(" Probability: " + probabilities[i]);

        }
        System.out.println();

        //calculate gaussian parameters
        for (i = 0; i < All_diseases.length; i++) {
            for (j = 0, k = 1; j < training_features[0].length - 1 && k < training_features[0].length; j++, k++) {
                x = -(Math.pow((test_features[k] - mean[i][j]), 2.0)) / (2.0 * (var [i][j]));

                gaussian[i][j] = (1 / Math.sqrt(2.0 * Math.PI *var [i][j]))* Math.exp(x);
                System.out.print(" x= " + x + " gaussian: " + gaussian[i][j]);

            }
            System.out.println();

        }

        //print gaussian values
        for (i = 0; i < All_diseases.length; i++) {
            for (j = 0; j < training_features[0].length - 1; j++) {
                System.out.print(" G: " + gaussian[i][j]);
            }
            System.out.println();
        }

        //calculate posterior values
        for (i = 0; i < All_diseases.length; i++) {
            for (j = 0; j < training_features[0].length - 1; j++) {
                prod = prod * gaussian[i][j];

            }
            p[i] = prod * probabilities[i];
            prod = 1.0;

        }
        //print the posterior values
        for (i = 0; i < All_diseases.length; i++) {
            posterior[i] = p[i];
            System.out.print(" Posterior: " + posterior[i]);
        }

        //sort the posterior values
        // Arrays.sort(p);
        for (i = 0; i < All_diseases.length; i++) {
            for (j = i + 1; j < All_diseases.length; j++) {
                double temp;
                if (p[i] > p[j]) {
                    temp = p[i];
                    p[i] = p[j];
                    p[j] = temp;
                }


            }

        }
        System.out.println();

        //print the sorted values
        for (i = 0; i < All_diseases.length; i++) {
            System.out.print(" " + p[i]);


        }
        System.out.println();
        //loop to check the disease
        for (i = 0; i < All_diseases.length; i++) {
            if (p[All_diseases.length - 1] == posterior[i]) {
                System.out.println("The image is classified as: " + All_diseases[i]);
                finalResult = All_diseases[i];
            }
        }
    }



    public static void main(String [] args)
    {
        DataClass.runner();
        //test data for 1 image  //image (SS)
        //double test_features[]= {37.0,1475212.0,0.004470956780813756,2.185662955889676,0.6346466181894274,297.8942765479055,1.2868753714134114,0.28057246489060195,7932.211594175549,64392.0,45.77971074973019,3.824749643844873,231.48650068632512,3647.907413253606,0.046744809324055836,569.9140952942043,0.0010845614834438684,0.21391746354854563};
        double test_features[] = { 0.0, 247.12952069714376, 225.5770378385176, 1217.0, 36074.0, 0.30607158215501756, 1.5453441603735187, 1.0955437799216727, 2.2380349058635867, 0.6098950189841953, 98.83911129742128, 5309.1723892361, 0.24756726279103283, 0.2187453895459463, 11099.776582151693, 4.048993825304251, 796.7238597675001 };

        double training_features1[][]=DataClass.training_features;
        String training_diseases1[]=DataClass.training_diseases;
        String[] All_diseases ={"BacterialSpot","Healthy","LateBlight","LeafCurl","MosaicVirus","SeptorialSpot"};
        NaiveBayes(training_features1,training_diseases1,test_features,All_diseases);
    }
}

