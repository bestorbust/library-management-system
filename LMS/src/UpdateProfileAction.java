
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class UpdateProfileAction extends  AbstractAction {
    private int userId;
    private String newName;
    private String newPassword;
 
    public UpdateProfileAction(Connection connection,int userId, String newName, String newPassword) {
        super(connection);
        this.userId = userId;
        this.newName = newName;
        this.newPassword = newPassword;
    }

    @Override
    public void execute() {
        try {
            String query = "UPDATE users SET username = ?, password = ? WHERE id = ?";
            java.sql.PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, newName);
            pst.setString(2, newPassword);
            pst.setInt(3, userId);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null,"Updated Profile successfully!");
            
        }
        catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error addind book: "+e.getMessage());
            
        }
    }
}
