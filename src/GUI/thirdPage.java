package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class thirdPage extends JFrame {
    private JPanel panel;
    private JLabel title, imageLabel, result1, result2;
    private JButton btnBack ,btnDetails;
    private JTable features;
    //private JTextArea features;

    public thirdPage(String fileName, String frameTitle) {
        setTitle(frameTitle);
        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.decode("#212121"));
        getContentPane().add(panel, BorderLayout.NORTH);
        GridBagConstraints c = new GridBagConstraints();

        title = new JLabel("thє grєєn вud ");
        title.setFont(new Font("Serif", Font.PLAIN, 24));
        title.setForeground(Color.decode("#35ff79"));
        imageLabel = new JLabel();
        btnDetails = new JButton("Less Details");
        btnBack = new JButton("Go Back");
        result1 = new JLabel("KNN Prediction :  " + firstPage.KNNResult);
        result2 = new JLabel("Naive-Bayes Prediction :  " + firstPage.NBResult);

        result1.setForeground(Color.decode("#ffffff"));
        result2.setForeground(Color.decode("#ffffff"));

        imageLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));

        //features
        String[][] data = {
                { "Area", String.valueOf(firstPage.test_features[4])},
                { "Form Factor", String.valueOf(firstPage.test_features[5])},
                { "Perimeter", String.valueOf(firstPage.test_features[3])},
                { "Aspect Ratio", String.valueOf(firstPage.test_features[7])},
                { "Rectangularity", String.valueOf(firstPage.test_features[6])},
                { "Entropy Mean", String.valueOf(firstPage.test_features[8])},
                { "Entropy Range", String.valueOf(firstPage.test_features[15])},
                { "Homogeneity Mean  ", String.valueOf(firstPage.test_features[9])},
                { "Homogeneity Range", String.valueOf(firstPage.test_features[13])},
                { "Contrast Mean", String.valueOf(firstPage.test_features[10])},
                { "Contrast Range", String.valueOf(firstPage.test_features[16])},
                { "Sum of Squares Mean", String.valueOf(firstPage.test_features[11])},
                { "Sum of Squares Range", String.valueOf(firstPage.test_features[14])},
                { "Angular Second Moment Mean", String.valueOf(firstPage.test_features[12])},
                { "Angular Second Moment Range", String.valueOf(firstPage.test_features[17])}
        };

        String[] columnNames = { "Feature", "Value" };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        features = new JTable(model);
        features.setPreferredScrollableViewportSize(new Dimension(400,240));
        features.setFillsViewportHeight(true);

        //j.setBounds(30, 40, 200, 100);

        JScrollPane sp = new JScrollPane(features);

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets (10,10,10,10);
        c.gridx = 1; c.gridy = 0; c.gridwidth=3;
        panel.add(title, c);
        c.gridx = -2; c.gridy = 1;  c.gridwidth=1;
        panel.add(imageLabel, c);
        c.gridx = 2; c.gridy = 1;
        panel.add(sp, c);
        c.gridx = 1; c.gridy = 2; c.gridwidth=3;
        panel.add(result1, c);
        c.gridx = 0; c.gridy = 3; c.gridwidth=3;
        panel.add(result2, c);
        c.gridx = 1; c.gridy = 4; c.gridwidth=3;
        panel.add(btnDetails, c);
        c.gridx = 1; c.gridy = 5; c.gridwidth=3;
        panel.add(btnBack, c);

        imageLabel.setIcon(new ImageIcon(fileName));

        btnDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new secondPage(fileName, frameTitle);
                dispose();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new firstPage(frameTitle);
                dispose();
            }
        });

        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}