package Task1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class LoginFrame extends JFrame{
    JTextField userName;
    JPasswordField password;
    JButton loginButton;
    JButton registerButton;
    JLabel userlabel, passlabel;
    JPanel panel;

    private DBConnect db;

    public LoginFrame(){
        db = new DBConnect();
        userlabel = new JLabel("Username:");
        userlabel.setBounds(20, 20, 80, 25);
        passlabel = new JLabel("Password:");
        passlabel.setBounds(20, 50, 80, 25);
        
        userName = new JTextField();
        userName.setBounds(100, 20, 165, 25);
        password = new JPasswordField();
        password.setBounds(100, 50, 165, 25);
        
        registerButton = new JButton("Register");
        registerButton.setBounds(100, 80, 90, 25);

        loginButton = new JButton("Login");
        loginButton.setBounds(200, 80, 80, 25);

        panel = new JPanel();
        panel.setLayout(null);
        panel.add(userlabel);
        panel.add(passlabel);
        panel.add(userName);
        panel.add(password);
        panel.add(registerButton);
        panel.add(loginButton);
        
        this.add(panel);
        this.setTitle("Login Form");
        this.setSize(300, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //==============Action Listeners================

        registerButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            this.dispose();
            RegisterFrame frame2 = new RegisterFrame();
            frame2.setVisible(true);
        });

         loginButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            String user = userName.getText();
            char[] pass = password.getPassword();

            if(user.isEmpty() || pass.length == 0){
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
            boolean result = db.CheckLogin(user, pass);
            if(result == true){
                JOptionPane.showMessageDialog(this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                Arrays.fill(pass, '\0'); // Clear password array for security
                this.dispose();
                WelcomeFrame frame3 = new WelcomeFrame(user);
                frame3.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                userName.setText("");
                password.setText("");
            }
            } catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Database connection error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }   
    public static void main(String[] args) {
        LoginFrame frame1 = new LoginFrame();
        frame1.setVisible(true);        
    }
}