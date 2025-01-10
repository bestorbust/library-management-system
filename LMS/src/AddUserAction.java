
import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class AddUserAction extends AbstractAction{
    private String Username;
    private String Password;
    private String role;
    
    public AddUserAction(Connection connection,String Username,String Password,String role){
        super(connection);
        this.Username=Username;
        this.Password=Password;
        this.role=role;
    }
    public void execute(){
    try {
        String roleQuery = "SELECT id FROM roles WHERE role_name = ?";
        PreparedStatement rolePst = connection.prepareStatement(roleQuery);
        rolePst.setString(1, role);
        ResultSet rs = rolePst.executeQuery();

        if (rs.next()) {
            int roleId = rs.getInt("id");

            String query = "INSERT INTO users(username, password, role_id) VALUES (?, ?, ?)";
            java.sql.PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, Username);
            pst.setString(2, Password);
            pst.setInt(3, roleId);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "User added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Error: Role not found.");
        }
    } catch (HeadlessException | SQLException e) {
        JOptionPane.showMessageDialog(null, "Error adding User: " + e.getMessage());
    }
}    
}




   