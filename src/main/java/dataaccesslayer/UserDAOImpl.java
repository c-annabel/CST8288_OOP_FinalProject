/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

/**
 *
 * @author HelloFriend
 */
import dataaccesslayer.DAOinterface.UserDAO;
import transferobjects.User;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import transferobjects.CredentialsDTO;

public class UserDAOImpl implements UserDAO {
    private CredentialsDTO cred;
    
    public UserDAOImpl(CredentialsDTO cred){
        this.cred = cred;
    }
    
    @Override
    public boolean registerUser(User u) {
        String sql = "INSERT INTO Users (name, email, password, user_type) VALUES (?, ?, ?, ?)";
        DataSource source = new DataSource(cred);
        if(isEmailRegistered(u.getEmail())){
            System.out.println("exist email");
            return false;
        }else {
            try(Connection con = source.createConnection();
                PreparedStatement stmt = con.prepareStatement(sql);) {
                
                stmt.setString(1, u.getName());
                stmt.setString(2, u.getEmail());
                stmt.setString(3, hashPassword(u.getPassword()));
                stmt.setString(4, u.getUserType());

                int rows = stmt.executeUpdate();
                return rows > 0;
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        return false;
    }

    @Override
    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
            PreparedStatement stmt = con.prepareStatement(sql);){
            
            stmt.setString(1, email);
            stmt.setString(2, hashPassword(password));

            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                return new User.UserBuilder()
                        .setUserId(rs.getInt("user_id"))
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setPassword(rs.getString("password"))
                        .setUserType(rs.getString("user_type"))
                        .build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isEmailRegistered(String email) {
        String sql = "SELECT user_id FROM Users WHERE email = ?";
        DataSource source = new DataSource(cred);

        try (Connection con = source.createConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // true if email found
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void take(String breakLog){
        
    }
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());

        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }
}
