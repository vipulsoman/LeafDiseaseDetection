package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class index extends JFrame {
    private JPanel panel;
    private JLabel title;
    private JButton btnTrain, btnTest;

    public index(String frameTitle) {
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

        btnTest = new JButton("Test Image");
        btnTrain = new JButton("Train");


        c.gridx = 1; c.gridy = 0; c.gridwidth = 4;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets (10,10,10,10);
        panel.add(title, c);
        c.gridx = 0; c.gridy = 1; c.gridwidth = 2;
        panel.add(btnTrain, c);
        c.gridx = 2; c.gridy = 1; c.gridwidth = 2;
        panel.add(btnTest, c);



        btnTrain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Training.Driver.runner();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        btnTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new firstPage(frameTitle);
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
