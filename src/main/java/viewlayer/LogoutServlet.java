/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewlayer;

import businesslayer.System_Control;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import transferobjects.CredentialsDTO;

/**
 *
 * @author Chen Wang
 */
public class LogoutServlet extends HttpServlet {
    private System_Control logic;
    private CredentialsDTO cred = new CredentialsDTO();
    private static final String DATABASE_USERNAME = "cst8288";
    private static final String DATABASE_PASSWORD = "cst8288";
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        cred.setPassword(DATABASE_PASSWORD);
        cred.setUsername(DATABASE_USERNAME);
        
        HttpSession session = request.getSession(false);
        logic = new System_Control(cred);
        String action = request.getParameter("action");
        String UserName = (String) session.getAttribute("name");
        if ("logout".equals(action)) {
            if (session != null) session.invalidate();
            response.sendRedirect("LoginServlet-URL");

        } else if ("break".equals(action)) {
            String breakTimeStr = request.getParameter("breakTime");
            int breakMinutes = 0;
            try {
                breakMinutes = Integer.parseInt(breakTimeStr);
            } catch (NumberFormatException e) {
                breakMinutes = 0;
            }
            response.getWriter().println("<p>Break started for " + breakMinutes + " minutes.</p>");
            String breakLog = UserName + "Break started for " + breakMinutes + " minutes";
            logic.UserTakeBreak(breakLog, UserName);
        }
    }
}
