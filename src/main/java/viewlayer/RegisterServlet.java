/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;

import datalayer.UserDAO;
import datalayer.UserDAOImpl;
import domain.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");

        user newUser = new user.UserBuilder()
                .setName(name)
                .setEmail(email)
                .setPassword(password)
                .setUserType(userType)
                .build();

        boolean success = userDAO.registerUser(newUser);

        if (success) {
            response.sendRedirect("register_success.html");
        } else {
            response.sendRedirect("register_fail.html");
        }
    }
}
