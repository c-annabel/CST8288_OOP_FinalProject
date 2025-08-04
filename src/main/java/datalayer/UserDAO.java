/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package datalayer;

import domain.user;

public interface UserDAO {
    boolean registerUser(user user);
    user loginUser(String email, String password);
    boolean isEmailRegistered(String email);
}
