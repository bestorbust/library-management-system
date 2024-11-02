import java.sql.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class FindUserRoleAction extends AbstractAction {
    

    private int userId;
    private String role;
    
    public FindUserRoleAction(Connection connection,int userId){
        super(connection);
        this.userId=userId;
    }
    @Override
    public void execute(){
        String roleName = null;
        String query = "SELECT roles.role_name FROM users " +
                       "JOIN roles ON users.role_id = roles.id " +
                       "WHERE users.id = ?";
        
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                roleName = rs.getString("role_name");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching role: " + e.getMessage());
        }
        
        role=roleName;
    }
    public String getRole(){
        return role;
    }
}