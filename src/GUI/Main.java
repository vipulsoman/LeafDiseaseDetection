package GUI;

import javax.swing.*;

class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
        index obj = new index("The Green Bud");
    }
}