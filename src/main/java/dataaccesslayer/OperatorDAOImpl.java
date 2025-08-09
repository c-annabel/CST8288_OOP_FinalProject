/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccesslayer;

/**
 * for the operator login and register,
 * also able to get operator break log from the database
 * @author Tirth Tao
 * @since modify by Chen Wang
 */
import transferobjects.OperatorsDTO;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import transferobjects.CredentialsDTO;
import dataaccesslayer.DAOinterface.OperatorDAO;

public class OperatorDAOImpl implements OperatorDAO {
    private CredentialsDTO cred;
    
    public OperatorDAOImpl(CredentialsDTO cred){
        this.cred = cred;
    }
    
    /**
     * register operator
     * @param u
     * @return boolean true mean success, false mean some error occur
     */
    @Override
    public boolean registerUser(OperatorsDTO u) {
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
    
    /**
     * login action for the main page, taking user email and password for Authentication
     * password is hashed before store in database
     * @param email
     * @param password
     * @return 
     */
    @Override
    public OperatorsDTO loginUser(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
        DataSource source = new DataSource(cred);
        
        try (Connection con = source.createConnection();
            PreparedStatement stmt = con.prepareStatement(sql);){
            
            stmt.setString(1, email);
            stmt.setString(2, hashPassword(password));

            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                return new OperatorsDTO.UserBuilder()
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
    
    /**
     * validator for register action
     * send error message when email address already register
     * @param email
     * @return 
     */
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
    
    /**
     * for take a break action
     * input the break minutes to database
     * @param breakLog
     * @param name 
     */
    @Override
    public void takeABreak(String breakLog, String name) {
        final String insertLogSQL       = "INSERT INTO logs (description) VALUES (?)";
        final String insertUsersLogSQL  = "INSERT INTO UsersLog (user_id, logs_ID) VALUES (?, ?)";
        final String findUserIdSQL      = "SELECT user_id FROM Users WHERE name = ?";

        DataSource source = new DataSource(cred);

        try (Connection con = source.createConnection()) {
            con.setAutoCommit(false);
            try {
                // 1) Find user_id by name
                int userId;
                try (PreparedStatement ps = con.prepareStatement(findUserIdSQL)) {
                    ps.setString(1, name);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("User not found (name not unique or missing): " + name);
                        }
                        userId = rs.getInt("user_id");
                    }
                }

                // 2) Insert log row
                int logsId;
                try (PreparedStatement ps = con.prepareStatement(insertLogSQL, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, breakLog);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Failed to obtain generated key for logs");
                        }
                        // Column index 1 is safe for MySQL generated keys
                        logsId = rs.getInt(1);
                    }
                }

                // 3) Link user ↔ log
                try (PreparedStatement ps = con.prepareStatement(insertUsersLogSQL)) {
                    ps.setInt(1, userId);
                    ps.setInt(2, logsId); // <-- UsersLog.logs_ID
                    ps.executeUpdate();
                }

                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true); // optional, good hygiene if connection is pooled
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * getter for Break log
     * @param name
     * @return 
     */
    @Override
    public List<String> getBreakLog(String name) {
        DataSource source = new DataSource(cred);
        List<String> logs = new ArrayList<>();
        String sql =
            "SELECT l.description " +
            "FROM logs l " +
            "JOIN UsersLog ul ON l.logs_Id = ul.logs_Id " +
            "JOIN Users u   ON ul.user_id = u.user_id " +
            "WHERE u.name = ? " +
            "  AND l.description LIKE 'BREAK:%' " +
            "ORDER BY l.logs_Id DESC";

        try (Connection con = source.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    logs.add(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    /**
     * 
     * @return List<OperatorsDTO> all operators
     */
    @Override
    public List<OperatorsDTO> getAllUsers(){
        List<OperatorsDTO> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        DataSource source = new DataSource(cred);

        try (Connection con = source.createConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OperatorsDTO user = new OperatorsDTO.UserBuilder()
                    .setUserId(rs.getInt("user_id"))
                    .setName(rs.getString("name"))
                    .setEmail(rs.getString("email"))
                    .setPassword(rs.getString("password"))
                    .setUserType(rs.getString("user_type"))
                    .build();
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    /**
     * hash function for password
     * @param password
     * @return String hashed Password
     * @throws NoSuchAlgorithmException 
     */
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
