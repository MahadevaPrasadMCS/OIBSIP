package Task1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

public class ReservationFrame extends JFrame {

    JTextField name, age;
    JLabel nameLabel, ageLabel, genderLabel, dateLabel;
    JComboBox<String> dropdown,gender;
    JComboBox<Integer> trainSelection;
    JComboBox<String> classDropdown;
    JComboBox<String> fromDropdown, toDropdown;
    JButton fetchButton, fetchTime, bookNow, backButton;
    JSpinner dateSpinner;

    private ArrayList<Integer> fetchedTrains = new ArrayList<>();
    private DefaultComboBoxModel<Integer> trainModel = new DefaultComboBoxModel<>();
    private DBConnect db;

    public ReservationFrame(String user) {
        
        db = new DBConnect();
        
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // --- Name ---
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        name = new JTextField();
        name.setBounds(150, 50, 150, 30);

        // --- Age ---
        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 100, 100, 30);
        age = new JTextField();
        age.setBounds(150, 100, 150, 30);

        // --- Gender ---
        String[] genders = {"Male", "FeMale", "Others"};
        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 150, 100, 30);
        gender = new JComboBox<>(genders);
        gender.setBounds(150, 150, 150, 30);

        // --- Train type dropdown ---
        String[] options = {"Express", "Superfast", "Shathabdi"};
        JLabel trainType = new JLabel("Train Type:");
        trainType.setBounds(50, 200, 100, 30);
        dropdown = new JComboBox<>(options);
        dropdown.setBounds(150, 200, 150, 30);

        // --- Class Type ---
        String[] classes = {"Sleeper", "3AC", "2AC", "1AC"};
        JLabel classType = new JLabel("Class Type:");
        classType.setBounds(50, 250, 100, 30);
        classDropdown = new JComboBox<>(classes);
        classDropdown.setBounds(150, 250, 150, 30);

        // --- from (place) to destination ---
        JLabel fromTo = new JLabel("From :");
        fromTo.setBounds(50, 300, 50, 30);
        String[] fromPlace = {"Delhi", "Mumbai", "Chennai", "Kolkata", "Bangalore", "Mysuru"};
        fromDropdown = new JComboBox<>(fromPlace);
        fromDropdown.setBounds(150, 300, 100, 30);

        JLabel destination = new JLabel("To :");    
        destination.setBounds(50, 350, 50, 30);
        String[] toPlace = {"Delhi", "Mumbai", "Chennai", "Kolkata", "Bangalore", "Mysuru"};
        toDropdown = new JComboBox<>(toPlace);
        toDropdown.setBounds(150, 350, 100, 30);

        // --- Date selection ---
        dateLabel = new JLabel("Date of Journey:");
        dateLabel.setBounds(50, 400, 150, 30);
        SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH);
        dateSpinner = new JSpinner(dateModel);
        dateSpinner.setBounds(150, 400, 150, 30);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd-MM-yyyy");
        dateSpinner.setEditor(dateEditor);

        // --- Fetch Trains Button ---
        fetchButton = new JButton("Fetch Trains");
        fetchButton.setBounds(50, 450, 150, 30);
        if(!dropdown.getSelectedItem().toString().isEmpty()){
            fetchButton.setEnabled(true);
        } else {
            fetchButton.setEnabled(false);
        }

        // --- Train selection combo box ---
        JLabel trainLabel = new JLabel("Select Train:");
        trainLabel.setBounds(50, 500, 100, 30);
        trainSelection = new JComboBox<>(trainModel);
        trainSelection.setBounds(150, 500, 150, 30);

        // --- Fetch Timings Button ---
        fetchTime = new JButton("Fetch Timings");
        fetchTime.setBounds(50, 550, 150, 30);
        fetchTime.setEnabled(false);

        // --- Book Now Button ---
        bookNow = new JButton("Book Now");
        bookNow.setBounds(50, 600, 150, 30);
        bookNow.setEnabled(false);

        // --- Back Button ---
        backButton = new JButton("Back");
        backButton.setBounds(300, 650, 150, 30);

        // --- Add components to panel ---
        panel.add(nameLabel); panel.add(name);
        panel.add(ageLabel); panel.add(age);
        panel.add(genderLabel); panel.add(gender);
        panel.add(trainType); panel.add(dropdown);
        panel.add(trainLabel); panel.add(trainSelection);
         panel.add(fromTo); panel.add(fromDropdown); 
        panel.add(destination); panel.add(toDropdown);
        panel.add(dateLabel); panel.add(dateSpinner);
        panel.add(dropdown); panel.add(fetchButton);
        panel.add(trainSelection);
        panel.add(classType);
        panel.add(classDropdown);
        panel.add(fetchTime); panel.add(bookNow);
        panel.add(backButton);

        this.add(panel);
        this.setTitle("Reservation Form");
        this.setSize(500, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // --- Action Listeners ---

        // Fetch trains dynamically
        fetchButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            fetchTrains();
        });

        // Fetch train timings
        fetchTime.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            fetchTimings();
        });

        // Book ticket
        bookNow.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            bookTrain(user);
        });
        
        backButton.addActionListener((ActionEvent e) -> {
            System.out.println(e.getSource());
            dispose();
            WelcomeFrame frame3 = new WelcomeFrame(user);
            frame3.setVisible(true);
        });
    }

    private void fetchTrains() {
        String selectedType = (String) dropdown.getSelectedItem();
        String fromPlace = (String) fromDropdown.getSelectedItem();
        String toPlace = (String) toDropdown.getSelectedItem();
            if(selectedType == null || selectedType.isEmpty() || fromPlace == null || fromPlace.isEmpty() || toPlace == null || toPlace.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please select a train type and places", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                fetchedTrains = db.fetchTrainNumber(selectedType, fromPlace, toPlace);

                trainModel.removeAllElements();
                for (Integer trainNo : fetchedTrains) {
                    trainModel.addElement(trainNo);
                }
                fetchTime.setEnabled(!fetchedTrains.isEmpty());
                JOptionPane.showMessageDialog(this, "There are " + fetchedTrains.size() + " available. Please select a train from the dropdown.", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (RuntimeException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(this, "Sorry there is a connection error in fetching trains. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    private void fetchTimings() {
       Integer selectedTrain = (Integer) trainSelection.getSelectedItem();
       String classType = (String) classDropdown.getSelectedItem();
       if(classType == "Sleeper"){
            classType = "sleeper_seats";
       }else{
            classType = "ac_seats";
       }
            if(selectedTrain != null && classType != null && !classType.isEmpty()) {
                try {
                    String time = db.fetchTrainTime(selectedTrain);
                    String trainName = db.fetchTrainName(selectedTrain,classType);
                    bookNow.setEnabled(true);
                    JOptionPane.showMessageDialog(this, "Train: " + trainName + "\nDeparture Time: " + time);
                } catch (RuntimeException ex) {
                    System.out.println(ex);
                    JOptionPane.showMessageDialog(this, "Sorry there is a connection error in fetching time. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a train first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    void bookTrain(String user) {
        String passengerName = name.getText();
        String genderText = gender.getSelectedItem().toString();
        String trainType = (String) dropdown.getSelectedItem();
        Date reservationDate = (Date) dateSpinner.getValue();
        int selectedTrain = (Integer) trainSelection.getSelectedItem();
        String classType = (String) classDropdown.getSelectedItem();
        String fromPlace = (String) fromDropdown.getSelectedItem();
        String toPlace = (String) toDropdown.getSelectedItem();

            if(passengerName.isEmpty() || genderText.isEmpty() || trainType.isEmpty() || reservationDate == null || selectedTrain == 0 || classType.isEmpty() || fromPlace.isEmpty() || toPlace.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                java.sql.Date sqlDate = new java.sql.Date(reservationDate.getTime());
                String msg = db.bookTicket(passengerName, genderText, trainType, sqlDate, selectedTrain, classType, fromPlace, toPlace);
                JOptionPane.showMessageDialog(this, msg);
                JOptionPane.showMessageDialog(this, "Ticket booked successfully! Redirecting to home page.");
                dispose();
                WelcomeFrame frame3 = new WelcomeFrame(user);
                frame3.setVisible(true);
            } catch (RuntimeException ex) {
               System.out.println(ex);
               JOptionPane.showMessageDialog(this, "Sorry there is a connection error in booking ticket. Please try again later", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    public static void main(String[] args) {
        ReservationFrame frame = new ReservationFrame(String.valueOf("User"));
        frame.setVisible(true);
    }
}
