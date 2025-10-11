package Task1;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class WelcomeFrame extends JFrame{
        JButton reservationButton;
        JButton cancellationButton;
        JButton logoutButton;
        JLabel messageLabel;
        JPanel panel;

    public WelcomeFrame(String user){

        messageLabel = new JLabel();
        messageLabel.setBounds(30,50,300,20);
        messageLabel.setText("Hello " + user +" Welcome to the Reservation System..");

        reservationButton = new JButton("Reservation");
        reservationButton.setBounds(30, 100, 120, 25);
        

        cancellationButton = new JButton("Cancellation");
        cancellationButton.setBounds(170, 100, 120, 25);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(140, 150, 100, 25);

        panel = new JPanel();
        panel.setLayout(null);
        panel.add(messageLabel);
        panel.add(reservationButton);
        panel.add(cancellationButton);
        panel.add(logoutButton);

        this.add(panel);
        this.setTitle("Welcome Form");
        this.setSize(400,400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //=============Action Listners=================

        reservationButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            this.dispose();
            ReservationFrame frame4 = new ReservationFrame(user);
            frame4.setVisible(true);
        });

        cancellationButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            this.dispose();
            CancellationFrame frame5 = new CancellationFrame(user);
            frame5.setVisible(true);
        });

        logoutButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            LoginFrame frame1 = new LoginFrame();
            frame1.setVisible(true);
            }
        });
    }
    public static void main(String[] args) {
        WelcomeFrame frame3 = new WelcomeFrame(String.valueOf("User"));
        frame3.setVisible(true);
    }
}
