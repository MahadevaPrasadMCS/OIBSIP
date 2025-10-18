package Task2;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class WelcomeFrame extends JFrame{
    public WelcomeFrame() {
        setTitle("Welcome Frame");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Welcome to the Application!", SwingConstants.CENTER);
        add(welcomeLabel);

        
    }
    public static void main(String[] args) {
        WelcomeFrame frame1 = new WelcomeFrame();
        frame1.setVisible(true);
    }
}