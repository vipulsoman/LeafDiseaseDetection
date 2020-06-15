package ImgSegmentation;

//package Plant;
public class Opening {
    int finalImage[][];
    public Opening(Masking obj) {
        finalImage = ImageOpening(obj.finalImage,obj.gh,obj.gw);
    }

    public static int[][] ImageOpening(int BinaryImage[][], int gh, int gw) {
        int i,j,flag,x,y;
        int newGh=gh+2;
        int newGw=gw+2;
        int image[][] = new int[newGw][newGh];
        int image1[][] = new int[newGw][newGh];
        int image2[][] = new int[newGw][newGh];
        int finalImg[][] = new int[gw][gh];
        for(i=0;i<gw;i++)
            for(j=0;j<gh;j++)
                image[i+1][j+1]=BinaryImage[i][j];

        int structure[][]= {
                {1,1,1},
                {1,1,1},
                {1,1,1}
        };

        //System.out.println("Image Before ImgSegmentation.Opening: ");
        //display(BinaryImage,gh,gw);

        //System.out.println("Temporary image 1: ");
        //display(image,m);

        //EdgeDetection.Erosion
        for(i=1;i<newGw-1;i++) {
            for(j=1;j<newGh-1;j++) {
                flag=1;
                x=-1;
                for(int k=0;k<3;k++) {
                    y=-1;
                    for(int l=0;l<3;l++) {
                        //System.out.println("x = " + x + "\ty = " + y);
                        if(image[i+x][j+y] != structure[k][l]) {
                            flag=0;
                            break;
                        }
                        y++;
                    }
                    x++;
                    //System.out.println("flag = " + flag);
                    if(flag == 0)
                        break;
                }
                if(flag == 1)
                    image1[i][j] = 1;
                else
                    image1[i][j] = 0;
            }
        }
        //System.out.println("Temporary image 2: ");
        //display(image1,m);

        //EdgeDetection.Dilation
        for(i=1;i<newGw-1;i++) {
            for(j=1;j<newGh-1;j++) {
                if(image1[i][j] == structure[1][1]) {
                    x=-1;
                    for(int k=0; k<3; k++, x++) {
                        y=-1;
                        for(int l=0; l<3; l++, y++) {
                            image2[i+x][j+y] = structure[k][l];
                        }
                    }
                }
            }
        }
        //System.out.println("Temporary image 3 : ");
        //display(image2,m);

        for(i=0;i<gw;i++)
            for(j=0;j<gh;j++)
                finalImg[i][j] = image2[i+1][j+1];

        //System.out.println("Image After ImgSegmentation.Opening : ");
        //display(finalImg,gh,gw);

        return finalImg;
    }

    public static void display(int image[][],int gh,int gw) {
        System.out.println();
        for(int i=0;i<gw;i++) {
            for(int j=0;j<gh;j++) {
                System.out.print(image[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
    /*
    public static void main(String args[]) {//throws IOException {
        int size=10;
        int BinaryImage[][] = {
                {0, 1, 1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        int Opened[][] = ImageOpening(BinaryImage,size);
    }

    */
}
