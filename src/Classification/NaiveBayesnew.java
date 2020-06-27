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
public class NaiveBayesnew {
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
    public static void NaiveBayes(double[][] training_features, String[] training_diseases, double[] test_features, String[] All_diseases){
        int i,j,m,n,cnt=0,k,num=0;
        double add=0.0,addv=0.0,prod=1.0;
        double mean[][]=new double[6][18];
        double var[][]=new double[6][18];
        int record_number[]= new int[60];
        int target_counts[]=new int[6];
        double probabilities[]=new double[6];
        double gaussian[][] = new double[6][18];
        double p[]=new double[6];
        double posterior[]=new double[6];
        double x;
   /*     for(i=0;i<training_features.length;i++){
            System.out.println(training_features[i].length);
        }  */

        for(i=0;i<All_diseases.length;i++){
            //   System.out.println(All_diseases[i]);
            cnt=0;
            for(j=0;j<training_diseases.length;j++){
                //   System.out.println(All_diseases[i]);
                if(All_diseases[i].equals(training_diseases[j])){
                    // System.out.print(All_diseases[i]);
                    record_number[cnt]=j;

                    cnt++;

                }



            }
            System.out.println();
            System.out.println(cnt);
            if(cnt>0){
                target_counts[i]=cnt;  //storing counts of diseases
                //function to calculate mean
                for(m=1;m<training_features[0].length;m++){
                    add=0.0;
                    for(n=0;n<cnt;n++){
                        //  System.out.print(record_number[n]);
                        add+=training_features[record_number[n]][m];
                        System.out.print(" "+add);
                    }
                    System.out.println();
                    mean[i][m-1]=add/cnt;


                    add=0.0;
                }
                //function to calculate variance
                for(m=1;m<training_features[0].length;m++){
                    addv=0.0;
                    for(n=0;n<cnt;n++){
                        addv+=((training_features[record_number[n]][m]-mean[i][m-1])*(training_features[record_number[n]][m]-mean[i][m-1]));
                    }

                    var[i][m-1]=addv/cnt;


                    addv=0.0;
                }



            }
            else
            {
                continue;
            }
        }

        //print mean and variance
        for(i=0;i<All_diseases.length;i++){
            for(j=0;j<training_features[0].length-1;j++){
                System.out.print(" m: "+mean[i][j]+" v: "+var[i][j]);
            }

            System.out.println();
        }

        //calculate probabilities for every disease
        for(i=0;i<All_diseases.length;i++){

            probabilities[i]=(double)target_counts[i]/(double)training_features.length;

        }
        //print the probabilities
        for(i=0;i<All_diseases.length;i++){

            System.out.print(" Probability: "+probabilities[i]);

        }
        System.out.println();

        //calculate gaussian parameters
        for(i=0;i<All_diseases.length;i++){
            for(j=0,k=1;j<training_features[0].length -1 && k<training_features[0].length;j++,k++){
                x= -(Math.pow((test_features[k]-mean[i][j]),2.0))/(2.0*(var[i][j]));

                gaussian[i][j]=(1/Math.sqrt(2.0*Math.PI*var[i][j]))*Math.exp(x);
                System.out.print(" x= "+x+" gaussian: "+gaussian[i][j]);

            }
            System.out.println();

        }

        //print gaussian values
        for(i=0;i<All_diseases.length;i++){
            for(j=0;j<training_features[0].length-1;j++){
                System.out.print(" G: "+gaussian[i][j]);
            }
            System.out.println();
        }

        //calculate posterior values
        for(i=0;i<All_diseases.length;i++){
            for(j=0;j<training_features[0].length-1;j++){
                prod=prod*gaussian[i][j];

            }
            p[i]=prod*probabilities[i];
            prod=1.0;

        }
        //print the posterior values
        for(i=0;i<All_diseases.length;i++){
            posterior[i]=p[i];
            System.out.print(" Posterior: "+posterior[i]);
        }

        //sort the posterior values
        // Arrays.sort(p);
        for(i=0;i<All_diseases.length;i++){
            for(j=i+1;j<All_diseases.length;j++){
                double temp;
                if(p[i]>p[j]){
                    temp=p[i];
                    p[i]=p[j];
                    p[j]=temp;
                }


            }

        }
        System.out.println();

        //print the sorted values
        for(i=0;i<All_diseases.length;i++){
            System.out.print(" "+p[i]);


        }
        System.out.println();
        //loop to check the disease
        for(i=0;i<All_diseases.length;i++){
            if(p[All_diseases.length-1]==posterior[i]){
                System.out.println("The image is classified as: "+All_diseases[i]);
                finalResult = All_diseases[i];
            }
        }
    }



    public static void main(String [] args)
    {
        //test data for 1 image  //image (Healthy)
        //double test_features[]= {100.0,239.1693960355296,175.34537347760278,18591.0, 441403.0,0.016048692017670958,0.09500897608820652,1.3639903425571658,1.4132475642258577,0.7775942388557907,34.43643152921076,4939.507552416781,0.4330061281573644,0.2852950716948001,12735.382315286311,3.7768093767703816,835.7126883445924,0.0012708561324221014};

        //double test_features[] = { 0.0, 247.12952069714376, 225.5770378385176, 1217.0, 36074.0, 0.30607158215501756, 1.5453441603735187, 1.0955437799216727, 2.2380349058635867, 0.6098950189841953, 98.83911129742128, 5309.1723892361, 0.24756726279103283, 0.2187453895459463, 11099.776582151693, 4.048993825304251, 796.7238597675001, 5.046799419070925E-4};

        double test_features[] = { 0.0, 257.6703320136022, 209.61393083476108, 1316.0, 35840.0, 0.260055525874341, 1.5070114718992504, 1.2292614855675983, 2.0488850225344293, 0.6569169296519434, 172.94974539361314, 5343.444924797025, 0.2558798848725225, 0.2416029292277893, 11305.509354293536, 3.8023463381400284, 570.4634745242416, 9.389248641038527E-4 };
        DataClass.runner();
        /*  System.out.println(DataClass.n);
          int i,j;
          System.out.println(DataClass.training_diseases.length);
          
          for(i=0;i<DataClass.training_diseases.length;i++){
              
                  System.out.println(" "+DataClass.training_diseases[i]);
              
           //   
          } */
        //   System.out.println();
        NaiveBayes(DataClass.training_features,DataClass.training_diseases,test_features,DataClass.All_diseases);
    }
}

