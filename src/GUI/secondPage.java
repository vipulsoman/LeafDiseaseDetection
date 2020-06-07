package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class secondPage extends JFrame {
    private JPanel panel;
    private JLabel title, imageLabel, result1, result2;
    private JButton btnBack, btnDetails;

    public secondPage(String fileName, String frameTitle) {
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
        btnDetails = new JButton("More Details");
        btnBack = new JButton("Go Back");
        result1 = new JLabel("KNN Prediction :  Mosaic Virus ");
        result2 = new JLabel("Naive-Bayes Prediction :  Mosaic Virus");

        result1.setForeground(Color.decode("#ffffff"));
        result2.setForeground(Color.decode("#ffffff"));

        imageLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets (10,40,10,40);
        c.gridx = 0; c.gridy = 0; //c.gridwidth=3;
        panel.add(title, c);

        c.gridx = 0; c.gridy = 1; //c.gridwidth=1;
        panel.add(imageLabel, c);

        c.gridx = 0; c.gridy = 2;
        panel.add(result1, c);

        c.gridx = 0; c.gridy = 3;
        panel.add(result2, c);

        c.gridx = 0; c.gridy = 4; //c.gridwidth=3;
        panel.add(btnDetails, c);

        c.gridx = 0; c.gridy = 5; //c.gridwidth=3;
        panel.add(btnBack, c);

        imageLabel.setIcon(new ImageIcon(fileName));

        btnDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new thirdPage(fileName, frameTitle);
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