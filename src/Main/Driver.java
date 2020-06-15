package Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Objects;

public class Driver {
    static final File folder = new File("images/input/");
    public static String directory;
    public static String file;
    public static String diseaseName;
    public static Path outputdpath ;
    public static Path outputfpath ;
    public static String inputfpath ;
    public static String outputpath ;
    public static void listFilesForFolder(final File folder) throws IOException {

        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                outputdpath =  Paths.get("images/output/"+ fileEntry.getName());
                diseaseName=fileEntry.getName();
                System.out.println("@------>  Working on Disease "+diseaseName);
                if(Files.notExists(outputdpath))
                Files.createDirectory(outputdpath);
                listFilesForFolder(fileEntry);
            } else {
                //System.out.println(fileEntry.getName());
                file=fileEntry.getName();
                System.out.println("------> File  "+file);
                inputfpath = fileEntry.getAbsolutePath();

                outputfpath =  Paths.get( outputdpath.toString()+"/"+file);
                if(Files.notExists(outputfpath))
                Files.createDirectory(outputfpath);
                outputpath=outputfpath.toString()+"/";
                ImSeg_SubDriver.runner();
                EdgeD_SubDriver.runner();
                ShapeF_SubDriver.runner();
                TextrExt_SubDriver.runner();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        listFilesForFolder(folder);
    }
}
/*

 */