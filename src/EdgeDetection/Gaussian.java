package EdgeDetection;

public class Gaussian {
    
    private static final double SQRT2PI = Math.sqrt(2 * Math.PI);

    public static int[][] GBlur (int[][] raw, int rad, double intens) {
        int height = raw.length;
        int width = raw[0].length;
        double norm = 0.;
        double intensSquared2 = 2 * intens * intens;
       
        double invIntensSqrPi = 1 / (SQRT2PI * intens);
        double[] mask = new double[2 * rad + 1];
        int[][] outGS = new int[height - 2 * rad][width - 2 * rad];

        //Create EdgeDetection.Gaussian kernel
        for (int x = -rad; x < rad + 1; x++) {
            double exp = Math.exp(-((x * x) / intensSquared2));

            mask[x + rad] = invIntensSqrPi * exp;
            norm += mask[x + rad];
        }

        //Convolute image with kernel horizontally
        for (int r = rad; r < height - rad; r++) {
            for (int c = rad; c < width - rad; c++) {
                double sum = 0.;

                for (int mr = -rad; mr < rad + 1; mr++) {
                    sum += (mask[mr + rad] * raw[r][c + mr]);
                }

                //Normalize channel after blur
                sum /= norm;
                outGS[r - rad][c - rad] = (int) Math.round(sum);
            }
        }

        //Convolute image with kernel vertically
        for (int r = rad; r < height - rad; r++) {
            for (int c = rad; c < width - rad; c++) {
                double sum = 0.;

                for(int mr = -rad; mr < rad + 1; mr++) {
                    sum += (mask[mr + rad] * raw[r + mr][c]);
                }

                //Normalize channel after blur
                sum /= norm;
                outGS[r - rad][c - rad] = (int) Math.round(sum);
            }
        }
        return outGS;
    }
}
