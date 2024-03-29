package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Classification.*;
import Training.*;

public class firstPage extends JFrame {
    private JPanel panel;
    private JLabel title, imageLabel;
    private JTextField fileName;
    private JButton btnSubmit, btnUpload, btnOpen;

    public static double test_features[] = new double[18];
    public static String KNNResult, NBResult;


    String file;

    public firstPage(String frameTitle) {
        setTitle(frameTitle);

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.decode("#212121")); //#5a5a5a
        getContentPane().add(panel, BorderLayout.NORTH);
        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel(" thє grєєn вud ");
        title.setFont(new Font("Serif", Font.PLAIN, 24));
        //title.setBackground(Color.decode("#ff97dc"));
        title.setForeground(Color.decode("#35ff79"));
        fileName = new JTextField(30);
        btnOpen = new JButton("Select Image");
        btnUpload = new JButton("Display Image");
        imageLabel = new JLabel();
        btnSubmit = new JButton("Analyse");

        c.gridx = 2; c.gridy = 0; c.gridwidth = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets (10,10,10,10);
        panel.add(title, c);
        c.gridx = -2; c.gridy = 1; c.gridwidth = 2;
        panel.add(fileName, c);
        c.gridx = 3; c.gridy = 1; c.gridwidth = 1;
        panel.add(btnOpen, c);
        c.gridx = 2; c.gridy = 2; c.gridwidth = 3;
        panel.add(btnUpload, c);

        btnOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                //JFileChooser j = new JFileChooser("C:\\Users\\shubh\\Desktop\\Programming_Projects\\Java Swing\\");
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION)
                    fileName.setText(j.getSelectedFile().getAbsolutePath());
                else
                    fileName.setText("");
            }
        });

        btnUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                c.gridx = 2; c.gridy = 3; c.gridwidth = 3;
                panel.add(imageLabel, c);

                file = fileName.getText();
                imageLabel.setIcon(new ImageIcon(file));
                imageLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));

                c.gridx = 2; c.gridy = 4; c.gridwidth = 4;
                panel.add(btnSubmit, c);

                pack();
                setLocationRelativeTo(null);
            }
        });

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Driver.inputfpath = file;
                Driver.outputpath = "images/test/op/";

                try {

                    ImSeg_SubDriver.runner();
                    EdgeD_SubDriver.runner();
                    ShapeF_SubDriver.runner();
                    TextrExt_SubDriver.runner();

                    assignValues.assignFeatures();          //assign the feature values to the test_features variable
                    DataClass.runner();

                    double training_features1[][]=DataClass.training_features;
                    String training_diseases1[]=DataClass.training_diseases;
                    String[] All_diseases ={"BacterialSpot","Healthy","LateBlight","LeafCurl","MosaicVirus","SeptorialSpot"};

                    int k=11;
                    KNN.knn(training_features1,training_diseases1,test_features,11);
                    //NaiveBayesnew.NaiveBayes(training_features1,training_diseases1,test_features,All_diseases);
                    NaiveBayesnew.NaiveBayes(DataClass.training_features,DataClass.training_diseases,test_features,DataClass.All_diseases);

                    assignValues.assignResult();            //assign the finalResult from KNN and NaiveBayes

                    System.out.println("KNN Result = " + KNNResult);
                    System.out.println("NB Result = " + NBResult);



                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                new secondPage(file, frameTitle);
                dispose();
            }
        });

        setVisible(true);
        //setSize(500, 100);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
