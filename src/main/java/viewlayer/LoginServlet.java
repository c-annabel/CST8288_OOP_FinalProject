package viewlayer;

import datalayer.UserDAO;
import datalayer.UserDAOImpl;
import domain.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        user loggedInUser = userDAO.loginUser(email, password);

        if (loggedInUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("userRole", loggedInUser.getUserType());
            response.sendRedirect("login_success.html");
        } else {
            response.sendRedirect("login_fail.html");
        }
    }
}
