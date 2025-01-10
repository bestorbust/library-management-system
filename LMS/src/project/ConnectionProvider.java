/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
package project;
import java.sql.*;

public class ConnectionProvider {
    private static final String URL = "jdbc:mysql://localhost:3307/lms";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "priya@9845";
    
    public static Connection getCon() {
        try {
            // Create a new connection each time
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
