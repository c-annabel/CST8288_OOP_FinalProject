/* File: SwingMVCDemo.java
 * Author: Stanley Pieda
 * Date: 2015
 * Description: Demonstration of DAO Design Pattern, MVC Design Pattern
 * References:
 * Ram N. (2013).  Data Access Object Design Pattern or DAO Pattern [blog] Retrieved from
 * http://ramj2ee.blogspot.in/2013/08/data-access-object-design-pattern-or.html
 */
package dataaccesslayer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import transferobjects.CredentialsDTO;

/**
 * Demonstration of DAO Design Pattern, MVC Design Pattern
 * @author Stanley Pieda
 */
public class DataSource {

    private Connection connection = null;
    private String url = "jdbc:mysql://localhost:3306/fleetmanagement?useSSL=false&allowPublicKeyRetrieval=true";
    private String username;
    private String password;

    public DataSource(CredentialsDTO creds) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        username=creds.getUsername();
        password=creds.getPassword();
        
    }

    /*
 * Only use one connection for this application, prevent memory leaks.
     */
    public Connection createConnection() throws SQLException {
    try {
        return DriverManager.getConnection(url, username, password);
    } catch (SQLException ex) {
        ex.printStackTrace();
        throw ex;
    }
}
}
