/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dataaccesslayer.DAOinterface;

import java.util.List;
import transferobjects.User;

/**
 * 
 * @author Tirth Rao
 * @since modify By Chen Wang
 */
public interface UserDAO {
    boolean registerUser(User user);
    User loginUser(String email, String password);
    boolean isEmailRegistered(String email);
    void takeABreak(String breakLog, String name);
    List<String> getBreakLog(String name);
    public List<User> getAllUsers();
}