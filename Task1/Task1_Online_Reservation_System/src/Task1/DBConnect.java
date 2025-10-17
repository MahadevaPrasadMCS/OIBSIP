package Task1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Random;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

class DBConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/user_db";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    private Connection Conn;

    public Connection getConnection(){
        try{
        Conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connecting to Database..."); 
        System.out.println("Driver and Database Connected Successfully");
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return Conn;
    }
    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            if (salt != null) {
                md.update(salt);
            }
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert bytes to hex format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password with salt", e);
        }
    }

    // Overload for callers that only provide a password (no salt)
    public static String hashPassword(String password) {
        return hashPassword(password, null);
    }

    public static boolean verifyPassword(String enteredPassword, String storedHash, byte[] storedSalt) {
    String newHash = hashPassword(enteredPassword, storedSalt);
    return newHash.equals(storedHash);
    }

    public boolean CheckLogin(String userName,char[] password) throws SQLException{
        getConnection();
        final PreparedStatement pst = Conn.prepareStatement("SELECT pass FROM users WHERE name = ?");
        pst.setString(1, userName);
        final ResultSet rs = pst.executeQuery();
        if(rs.next()){
            String storedHash = rs.getString("pass");
            boolean ok = verifyPassword(String.valueOf(password), storedHash, null);
            rs.close();
            pst.close();
            Conn.close();
            return ok;
        } else {
            rs.close();
            pst.close();
            Conn.close();
            return false;
        }
    }
    public int userRegister(int userId, String userName,char[] userPass) throws SQLException{
        getConnection();

        String sql = "SELECT id FROM users where id = ?";
        String query = "INSERT INTO users(id,name, pass) VALUES(?, ?, ?)"; 
        
        PreparedStatement st = Conn.prepareStatement(sql);
        st.setInt(1,userId);
        ResultSet rs = st.executeQuery();
        if(rs.next()){
            rs.close();
            st.close();
            Conn.close();
            return 2;
        }

        PreparedStatement pst = Conn.prepareStatement(query);
        String password = hashPassword(String.valueOf(userPass));
        pst.setInt(1, userId);
        pst.setString(2, userName);
        pst.setString(3, password);
        
        pst.executeUpdate();
        rs.close();
        pst.close();
        Conn.close();
        return 1;
    }

    public ArrayList<Integer> fetchTrainNumber(String trainType, String fromPlace, String toPlace){
        getConnection();
        ArrayList<Integer> trainNumbers = new ArrayList<>();
        try{
            String query = "SELECT train_no FROM trains WHERE train_type = ? and fromPlace = ? and toPlace = ?";
            PreparedStatement pst = Conn.prepareStatement(query);
            pst.setString(1, trainType);
            pst.setString(2, fromPlace);
            pst.setString(3, toPlace);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                trainNumbers.add(rs.getInt("train_no"));
            }
            rs.close();
            pst.close();
            Conn.close();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
        return trainNumbers;
    }
    public String fetchTrainTime(int trainNo){
        getConnection();
        try{
            String query = "select availTime from trains where train_no = ?";
            PreparedStatement pst = Conn.prepareStatement(query);
            pst.setInt(1,trainNo);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String time = rs.getString("availTime");
            rs.close();
            pst.close();    
            Conn.close();
            return time;
        }catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    public String fetchTrainName(int trainNo,String classType){
        getConnection();
        try{
            String query = "select train_name from trains where train_no = ?";
            String query2 = "select ac_seats,sleeper_seats from trains where train_no = ?";
            PreparedStatement pst = Conn.prepareStatement(query);
            PreparedStatement pst2 = Conn.prepareStatement(query2);
            pst2.setInt(1, trainNo);
            pst.setInt(1, trainNo);
            ResultSet rs = pst.executeQuery();
            ResultSet rs2 = pst2.executeQuery();
            if(classType.equals("ac_seats")){
                rs2.next();
                int acSeats = rs2.getInt("ac_seats");
                if(acSeats <= 0){
                    return "No AC seats available";
                }
                else{
                    int updatedSeats = acSeats - 1;
                    String updateQuery = "UPDATE trains SET ac_seats = ? WHERE train_no = ?";
                    PreparedStatement updatePst = Conn.prepareStatement(updateQuery);
                    updatePst.setInt(1, updatedSeats);
                    updatePst.setInt(2, trainNo);
                    updatePst.executeUpdate();
                    updatePst.close();
                }
            } else if(classType.equals("sleeper_seats")){
                rs2.next();
                int sleeperSeats = rs2.getInt("sleeper_seats");
                if(sleeperSeats <= 0){
                    return "No Sleeper seats available";
                }
                else{
                    int updatedSeats = sleeperSeats - 1;
                    String updateQuery = "UPDATE trains SET sleeper_seats = ? WHERE train_no = ?";
                    PreparedStatement updatePst = Conn.prepareStatement(updateQuery);
                    updatePst.setInt(  1, updatedSeats);
                    updatePst.setInt(2, trainNo);
                    updatePst.executeUpdate();
                    updatePst.close();
                }
            }
            String name;
            rs.next();
            name = rs.getString("train_name");
            rs.close();
            pst.close();
            Conn.close();
            return name;
        }
        catch(SQLException ex){
            throw new RuntimeException(ex);
        }
    }
    public String bookTicket(String userName, String gender, String trainType,Date reservationDate, int train_no, String classType, String fromPlace, String toPlace){
        getConnection();
        int num = 100000 + new Random().nextInt(900000);
        String pnr = "PNR-" + num;
        try{
            String query = "INSERT INTO reservations(pnr, name, gender, reservationDate, train_type, class_type, fromPlace, toPlace, train_no) VALUES(?, ?, ?, ?, ?, ?,?,?,?)";
            PreparedStatement pst = Conn.prepareStatement(query);
            pst.setString(1, pnr);
            pst.setString(2, userName);
            pst.setString(3, gender);
            pst.setDate(4, reservationDate);
            pst.setString(5, trainType);
            pst.setString(6, classType);
            pst.setString(7, fromPlace);
            pst.setString(8, toPlace);
            pst.setInt(9, train_no);
            pst.executeUpdate();
            pst.close();
            Conn.close();
            return "Ticket booked successfully! Your PNR is: " + pnr;            
            }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public String checkUser(String pnr){
        getConnection();
        try{
            String query = "SELECT * FROM reservations WHERE pnr = ?";
            PreparedStatement pst = Conn.prepareStatement(query);
            pst.setString(1, pnr);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                String PNR = rs.getString("pnr");
                String username = rs.getString("name");
                String gender = rs.getString("gender");
                Date date = rs.getDate("reservationDate");
                String train_type = rs.getString("train_type");
                String class_type = rs.getString("class_type");
                String from_place = rs.getString("fromPlace");
                String to_place = rs.getString("toPlace");
                int train_no = rs.getInt("train_no");
                CancellationFrame.reservationDetails(PNR, username, gender, date, train_type, train_no, class_type, from_place, to_place);
                rs.close();
                pst.close();
                Conn.close();
                return " ";
            } else {
                rs.close();
                pst.close();
                Conn.close();
                return "Invalid PNR!";
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public int cancelReservation(String pnr){
        getConnection();
        try{
            String query = "DELETE FROM reservations WHERE pnr = ?";
            PreparedStatement pst = Conn.prepareStatement(query);
            pst.setString(1, pnr);
            int rs = pst.executeUpdate();
            pst.close();
            Conn.close();
            return rs;
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}