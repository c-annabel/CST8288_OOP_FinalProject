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
import transferobjects.OperatorsDTO;
/**
 * LoginServlet (View Layer)
 * Handles the initial login page and authentication.
 * If authentication is successful, redirects to the FrontController.
 *
 * Handles user login authentication.
 * This servlet verifies user credentials and creates an HTTP session
 * for authenticated users.
 */

/**
 * servlet file taking get and post method for the fleet management system
 * using system_Control.java as business logic platform
 * @author Chen Wang
 */
public class ViewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DATABASE_USERNAME = "cst8288";
    private static final String DATABASE_PASSWORD = "cst8288";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Display the login form
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if(request.getParameter("login") != null){
            PageCreation.loginPage(request, out);
        }else if(request.getParameter("Register") != null){
            response.sendRedirect("register.html");
        }else{
            response.sendRedirect("index.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CredentialsDTO creds = new CredentialsDTO();
        creds.setUsername(DATABASE_USERNAME);
        creds.setPassword(DATABASE_PASSWORD);
        System_Control logic = new System_Control(creds);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
//      doGet(request, response); // Redisplay login page with error message
        
        if(request.getParameter("login") != null){
            OperatorsDTO loginUser = logic.login(request.getParameter("Email"), request.getParameter("password"));
            if(loginUser != null){
               session.setAttribute("login", true);
               session.setAttribute("name", loginUser.getName());
               session.setAttribute("role", loginUser.getUserType());
               response.sendRedirect("HomeServlet");
               
            }else{
               request.setAttribute("errorMessage", "Invalid username or password. Please try again.");
               doGet(request, response);
            }
        }else if(request.getParameter("Register") != null){
            OperatorsDTO u = new OperatorsDTO.UserBuilder()
                    .setEmail(request.getParameter("email"))
                    .setName(request.getParameter("name"))
                    .setPassword(request.getParameter("password"))
                    .setUserType(request.getParameter("userType"))
                    .setUserId(0)
                    .build();
            
            if(logic.register(u)){
                out.println("<script type='text/javascript'>");
                out.println("alert('Registration successful! Redirecting to homepage...');");
                out.println("window.location.href='index.html';");
                out.println("</script>");
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('Registration failed.');");
                out.println("window.history.back();");
                out.println("</script>");
            }
            
        }
        
        Boolean isAuthenticated = Boolean.TRUE.equals(session.getAttribute("authenticated"));
        if (isAuthenticated) {
            PageCreation.loginPage(request, out);
        }
        
    }
}
