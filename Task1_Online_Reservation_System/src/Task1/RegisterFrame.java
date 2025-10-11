package Task1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.sql.SQLException;

public class RegisterFrame extends JFrame{
        JTextField id;
        JTextField userName;
        JPasswordField password;
        JButton backButton;
        JButton submitButton;
        JLabel idLabel,userLabel, passLabel;
        JPanel panel;
    public RegisterFrame(){

        idLabel = new JLabel("Id:");
        idLabel.setBounds(20, 20, 80, 25);
        userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 50, 80, 25);
        passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 80, 80, 25);
        
        id = new JTextField();
        id.setBounds(100, 20, 165, 25);
        userName = new JTextField();
        userName.setBounds(100, 50, 165, 25);
        password = new JPasswordField();
        password.setBounds(100, 80, 165, 25);

        backButton = new JButton("Back");
        backButton.setBounds(100,120,80,25);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200,120,80,25);

        panel = new JPanel();
        panel.setLayout(null);
        panel.add(idLabel);
        panel.add(userLabel);
        panel.add(passLabel);
        panel.add(id);
        panel.add(userName);
        panel.add(password);
        panel.add(backButton);
        panel.add(submitButton);
        
        this.add(panel);
        this.setTitle("Registration Form");
        this.setSize(300, 220);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //=============Action Listners=================
    
            backButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            this.dispose();
            LoginFrame frame1 = new LoginFrame();
            frame1.setVisible(true); 
        });

          submitButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            int userId;
            String username = userName.getText();
            char[] pass = password.getPassword();
            if(username.isEmpty() || pass.length == 0 || id.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill all the details", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                userId = Integer.parseInt(id.getText());
            }
            catch(NumberFormatException ne){
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                DBConnect db = new DBConnect();
                int res = db.userRegister(userId,username,pass);
                if(res == 2){
                    id.setText("");
                    JOptionPane.showMessageDialog(this, "UserID has been taken try with other ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Your account has been created successfully! Redirecting to Login Page", "Success", JOptionPane.INFORMATION_MESSAGE);
                    Arrays.fill(pass, '\0');
                    this.dispose();
                    LoginFrame frame1 = new LoginFrame();
                    frame1.setVisible(true);
                }
            }
            catch(SQLException ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Your account could not be created. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }
    public static void main(String args[]){
        RegisterFrame frame2 = new RegisterFrame();
        frame2.setVisible(true); 
    }
}
