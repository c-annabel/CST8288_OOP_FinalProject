/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transferobjects;

/**
 * a DTO used to store and transfer DBMS login credentials
 * between businesslayer and dataacesslayer
 * @author kriger
 */

public class CredentialsDTO {
    private String username;
    private String password;

    /**
     * simple getter for user name portion of the credentials
     * @return user name portion of the credentials
     */
    public String getUsername() {
        return username;
    }

    /**
     * simple setter for user name portion of the credentials
     * @param username user name portion of the credentials
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * simple getter for password portion of the credentials
     * @return password portion of the credentials
     */
    public String getPassword() {
        return password;
    }

    /**
     * simple setter for password portion of the credentials
     * @param password password portion of the credentials
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}//class
