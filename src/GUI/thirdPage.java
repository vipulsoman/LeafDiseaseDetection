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
        result1 = new JLabel("KNN Prediction :  Mosaic Virus");
        result2 = new JLabel("Naive Bayes Prediction  :  Mosaic Virus ");

        result1.setForeground(Color.decode("#ffffff"));
        result2.setForeground(Color.decode("#ffffff"));

        imageLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));

        //features
        String[][] data = {
                { "Area", "26376" },
                { "Form Factor", "0.19402943676497836" },
                { "Entropy Mean", "1.6719866350768626" },
                { "Entropy Range", "4.204318021092474" },
                { "Homogeneity Mean  ", "0.682312377753786" },
                { "Homogeneity Range", "0.08485436691438598" },
                { "Perimeter", "1307.0" },
                { "Aspect Ratio", "0.9004080511633751" },
                { "Contrast Mean", "267.73086219520883" },
                { "Contrast Range", "1268.4025336131135" },
                { "Sum of Squares Mean", "3202.456126228885" },
                { "Sum of Squares Range", "9838.438988702219" },
                { "Rectangularity", "1.9748485653549155" },
                { "Angular Second Moment Mean", "0.4357475156795096" },
                { "Angular Second Moment Range", "6.465582331463026E-4" }
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