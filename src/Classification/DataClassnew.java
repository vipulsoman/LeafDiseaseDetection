package Classification;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class DataClassnew {

    public static double training_features[][];
    public static String training_diseases[];
    public static String[] All_diseases ={"BacterialSpot","Healthy","LateBlight","LeafCurl","MosaicVirus","SeptorialSpot"};
    static int n=0;

    public static void runner() {
        JSONParser jsonParser = new JSONParser();
        try {
            Object obj = jsonParser.parse(new FileReader("DataDirectory/FeatureData.json"));
            JSONArray jsonArray = (JSONArray) obj;
            training_features=new double[jsonArray.size()][17];
            training_diseases=new String[jsonArray.size()];
            jsonArray.forEach(leaf -> parseObj((JSONObject) leaf));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    private static void parseObj(JSONObject leaf) {
        training_features[n][0] = Double.parseDouble(leaf.get("id").toString());
        training_features[n][1] = (double) leaf.get("length");
        training_features[n][2] = (double) leaf.get("width");
        training_features[n][3] = (double) leaf.get("perimeter");

        //   training_features[n][4] = (long) leaf.get("area");
        training_features[n][4] = (double) leaf.get("formFactor");
        training_features[n][5] = (double) leaf.get("rectangular");
        training_features[n][6] = (double) leaf.get("aspectRatio");
        training_features[n][7] = (double) leaf.get("mentropy");
        training_features[n][8] = (double) leaf.get("mhomo");
        training_features[n][9] = (double) leaf.get("mcontrast");
        training_features[n][10] = (double) leaf.get("msos");
        training_features[n][11] = (double) leaf.get("masm");
        training_features[n][12] = (double) leaf.get("rhomo");
        training_features[n][13] = (double) leaf.get("rsos");
        training_features[n][14] = (double) leaf.get("rentropy");
        training_features[n][15] = (double) leaf.get("rcontrast");
        training_features[n][16] = (double) leaf.get("rasm");

        training_diseases[n]=(String) leaf.get("disease");
        n++;
    }

}
