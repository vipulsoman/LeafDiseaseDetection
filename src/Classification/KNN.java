
package Classification;
import java.lang.Math;

public class KNN {
    public static String finalResult;
    public static void knn(double[][] training_features, String[] disease, double[] test_features, int k) {
        int i, j, count = 0, num = 0, m, n;
        int count1[] = new int[100];
        double o_diseaseid[] = new double[1000];
        double image_id[] = new double[1000];
        double sum1[] = new double[1000];
        double disease_id[] = new double[1000];
        double sub = 0.0, sum = 0.0, temp, flag = 1.0, temp1 = 0.0, inv = 0.0;
        double distance[] = new double[1000];
        String out_disease[] = new String[100];
        System.out.println("Distances from training to test datasets: ");
        //calculate distance from test data to training data using Euclidean distance
        for (i = 0; i < training_features.length; i++) {

            sum = 0.0;
            for (j = 1; j < training_features[0].length; j++) {


                sub = Math.abs(training_features[i][j] - test_features[j]);

                sum = sum + sub;
                sub = 0.0;
            }
            distance[i] = sum;
            System.out.println("Image " + flag + " =" + sum);
            image_id[count] = training_features[i][0];
            count++;
            flag++;
        }
        //sort the distances
        for (i = 0; i < training_features.length; i++) {

            for (j = i + 1; j < training_features.length; j++) {

                if (distance[i] > distance[j]) {

                    temp = distance[i];
                    temp1 = image_id[i];
                    distance[i] = distance[j];
                    image_id[i] = image_id[j];
                    distance[j] = temp;
                    image_id[j] = temp1;
                }
            }
        }
        //to display the sorted distances
        System.out.println("Ater sorting: ");
        for (i = 0; i < training_features.length; i++) {
            System.out.println("image " + image_id[i] + ": " + distance[i]);
        }
        //number o clusters

        //or k=1
        if (k == 1) {

            for (i = 0; i < training_features.length; i++) {

                for (j = 0; j < training_features.length; j++) {

                    if (training_features[j][i] == image_id[0]) {

                        System.out.print("image id " + test_features[0] + " belongs to disease type: " + disease[j]);
                    }
                }
            }
        }

        else if (k > 1) {

            int cnt = 0;
            System.out.println("k= " + k + " hence, the diseases are :");
            for (i = 0; i < k; i++) {

                for (j = 0; j < training_features.length; j++) {

                    if (image_id[i] == training_features[j][cnt]) {

                        disease_id[num] = image_id[i];

                        out_disease[num] = disease[j];

                        System.out.println(disease_id[num] + ": " + out_disease[num]);

                        num++;
                    }

                }
            }

            String stemp = " ";
            cnt = 1;
            count = 1;
            num = 0;
            int max_count = 1, equal = 0;
            for (i = 0; i < k; i++) {

                for (j = i + 1; j < k; j++) {

                    if (out_disease[i].equals(out_disease[j])) {

                        ++cnt;
                    }
                    else
                        continue;
                }
                count1[i] = cnt;

                cnt = 1;
                if (count1[i] > max_count) {
                    stemp = out_disease[i];
                    max_count = count1[i];

                }
                else if (count1[i] == max_count) {
                    stemp = " ";
                }
            }



            cnt = 0;
            if (stemp == " ") {
                System.out.println();
                System.out.println();
                System.out.println("Inverse weighted measure: ");
                for (m = 0; m < k; m++) {
                    sum = 0.0;
                    for (i = 0; i < training_features.length; i++) {

                        for (j = 0; j < k; j++) {

                            if (disease_id[i] == image_id[j]) {

                                if (disease_id[m] == disease_id[i]) {

                                    n = 1;
                                }
                                else {

                                    n = 0;
                                }
                                inv = ((1 / (distance[j] * distance[j])) * n);

                                sum = sum + inv;



                            }
                            else
                                continue;
                        }

                    }
                    o_diseaseid[m] = disease_id[m];

                    sum1[m] = sum;
                    //printing the inverse distances
                    // System.out.println(o_diseaseid[m]);
                    System.out.println(sum1[m]);

                    sum = 0.0;
                }

                for (i = 0; i < k; i++) {
                    for (j = i + 1; j < k; j++) {
                        if (out_disease[i] == out_disease[j]) {
                            System.out.println(out_disease[i] + " " + out_disease[j]);
                            System.out.println(sum1[i] + " " + sum1[j]);
                            sum1[i] += sum1[j];
                            sum1[j] = 0.0;

                            System.out.println(sum1[i]);



                        }

                    }
                }
                System.out.println();
                for (i = 0; i < k; i++) {
                    System.out.println(sum1[i] + ",");
                }
                System.out.println();
                String s = " ";
                //sorting the inverse distances
                temp = 0.0;
                for (i = 0; i < k; i++) {

                    for (j = i + 1; j < k; j++) {

                        if (sum1[i] > sum1[j]) {

                            temp = sum1[i];
                            temp1 = o_diseaseid[i];
                            s = out_disease[i];

                            sum1[i] = sum1[j];
                            o_diseaseid[i] = o_diseaseid[j];
                            out_disease[i] = out_disease[j];

                            sum1[j] = temp;
                            o_diseaseid[j] = temp1;
                            out_disease[j] = s;


                        }
                    }

                }
                for (i = 0; i < k; i++) {
                    System.out.println("or " + out_disease[i] + "= " + sum1[i]);
                }


                stemp = out_disease[k - 1];
                System.out.println("Since inverse distance measure o disease " + stemp + " is highest: ");
                System.out.println("Image " + test_features[0] + " is o " + stemp);
                finalResult = stemp;
            }


            else {

                System.out.println("Since " + stemp + " is majority, ");
                System.out.println("image " + test_features[0] + " is o " + stemp);
                finalResult = stemp;
            }


        }
    }



    public static void main(String [] args) {
        int k = 21;
        //training data or 3 images (i: mean,range,shape; j: image; k: features)

        //test data or 1 image  //image 7 (MV)

        //double test_features[] = { 0.0, 247.12952069714376, 225.5770378385176, 1217.0, 36074.0, 0.30607158215501756, 1.5453441603735187, 1.0955437799216727, 2.2380349058635867, 0.6098950189841953, 98.83911129742128, 5309.1723892361, 0.24756726279103283, 0.2187453895459463, 11099.776582151693, 4.048993825304251, 796.7238597675001, 5.046799419070925E-4};
        double test_features[] = { 0.0, 257.6703320136022, 209.61393083476108, 1316.0, 35840.0, 0.260055525874341, 1.5070114718992504, 1.2292614855675983, 2.0488850225344293, 0.6569169296519434, 172.94974539361314, 5343.444924797025, 0.2558798848725225, 0.2416029292277893, 11305.509354293536, 3.8023463381400284, 570.4634745242416, 9.389248641038527E-4 };

        //double test_features[] = {0.0, 231.138486626, , 50668.0, , , , , 0.902716613823443, , ,168.46888929898108 , , ,0.22600537582291647 ,8620.082923020438 , ,}
        DataClass.runner();
        knn(DataClass.training_features, DataClass.training_diseases, test_features, k); //not sure about the call dont know which disease array is req -all disease or training diseas

    }
}
