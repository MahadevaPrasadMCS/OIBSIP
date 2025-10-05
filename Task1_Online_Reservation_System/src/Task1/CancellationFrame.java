package Task1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Date;

public class CancellationFrame extends JFrame {
    private static final int len = 10;
    private static final String pre_fix = "PNR-";
    private DBConnect db;

    private static JLabel PNR, userName, userGender, reservationDate, trainType, classType, fromPlace, toPlace, trainNo; 

    public CancellationFrame(String user) {
            
        db = new DBConnect();

        // --- Initialize Labels ---
        PNR = new JLabel("PNR: ");
        PNR.setBounds(50, 200, 400, 25);

        userName = new JLabel("Name: ");
        userName.setBounds(50, 230, 400, 25);

        userGender = new JLabel("Gender: ");
        userGender.setBounds(50, 260, 400, 25);

        reservationDate = new JLabel("Reservation Date: ");
        reservationDate.setBounds(50, 290, 400, 25);

        trainType = new JLabel("Train Type: ");
        trainType.setBounds(50, 320, 400, 25);

        trainNo = new JLabel("Train No: ");
        trainNo.setBounds(50, 350, 400, 25);

        classType = new JLabel("Class Type: ");
        classType.setBounds(50, 380, 400, 25);

        fromPlace = new JLabel("From: ");
        fromPlace.setBounds(50, 410, 400, 25);

        toPlace = new JLabel("To: ");
        toPlace.setBounds(50, 440, 400, 25);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel pnrLabel = new JLabel("Enter PNR Number: ");
        pnrLabel.setBounds(50, 50, 150, 25);

        JTextField pnrTextField = new JTextField();
        pnrTextField.setBounds(150, 50, 150, 25);

        JButton fetchButton = new JButton("Fetch");
        fetchButton.setBounds(150, 100, 80, 25);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 500, 120, 25);
        cancelButton.setEnabled(false); // disable until fetch is successful

        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 500, 80, 25);

        panel.add(pnrLabel);
        panel.add(pnrTextField);
        panel.add(fetchButton);
        panel.add(cancelButton);
        panel.add(backButton);
        panel.add(PNR); panel.add(userGender); panel.add(reservationDate);
        panel.add(userName); panel.add(trainType); panel.add(trainNo);
        panel.add(classType); panel.add(fromPlace); panel.add(toPlace);

        this.add(panel);
        this.setTitle("Cancellation Frame");
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --- Fetch button action ---
        fetchButton.addActionListener((ActionEvent e) -> {
            String pnr = pnrTextField.getText();
            if(!validatePNR(pnr)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid PNR.", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            try {
                String res = db.checkUser(pnr);
                // Enable cancel button only if reservation exists
                if (!res.contains("Invalid PNR!")) {
                    JOptionPane.showMessageDialog(this, "Reservation found. You can proceed to cancel.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    cancelButton.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "No reservation found with the provided PNR. Please Verify your PNR number again.", "Error", JOptionPane.ERROR_MESSAGE);
                    cancelButton.setEnabled(false);
                }
            } catch (RuntimeException ex) {
                System.out.println("SQL Error: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error fetching reservation details. Try after sometimes", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Cancel button action ---
        cancelButton.addActionListener((ActionEvent e) -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                    String pnr = pnrTextField.getText();
                    if(!validatePNR(pnr)) {
                        JOptionPane.showMessageDialog(this, "Please enter a valid PNR.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try{
                    int rs = db.cancelReservation(pnr);
                    if (rs > 0) {
                        JOptionPane.showMessageDialog(this, "Reservation cancelled successfully.");
                        cancelButton.setEnabled(false);
                        this.dispose();
                        WelcomeFrame frame3 = new WelcomeFrame(user);
                        frame3.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(this, "No reservation found with the provided PNR.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (RuntimeException ex) {
                    System.out.println("SQL Error: " + ex.getMessage());
                    JOptionPane.showMessageDialog(this, "Error cancelling reservation. Try after sometimes", "Error", JOptionPane.ERROR_MESSAGE);       
                }
            }
        });
        // --- Back button action ---
        backButton.addActionListener((ActionEvent e) -> {
            this.dispose();
            WelcomeFrame frame3 = new WelcomeFrame(user);
            frame3.setVisible(true);
        });
    }

    public static void reservationDetails(String pnr, String username, String gender, Date reservationdate, String train_type, int train_no, String class_type, String from_place, String to_place) {
        PNR.setText("PNR: " + pnr);
        userName.setText("Name: " + username);
        userGender.setText("Gender: " + gender);
        reservationDate.setText("Reservation Date: " + reservationdate);
        trainType.setText("Train Type: " + train_type);
        trainNo.setText("Train No: " + train_no);
        classType.setText("Class Type: " + class_type);
        fromPlace.setText("From: " + from_place);
        toPlace.setText("To: " + to_place);
    }

    public boolean validatePNR(String pnr) {
        return pnr != null && pnr.startsWith(pre_fix) && pnr.length() == len;
    }
    public static void main(String[] args) {
        CancellationFrame frame4 = new CancellationFrame(String.valueOf("User"));
        frame4.setVisible(true);
    }
}
