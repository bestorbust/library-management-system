
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
public class UpdateBookAction extends AbstractAction{
    
    private int bookID;
    private String title;
    private int quantity;
    
    public UpdateBookAction(Connection connection,int bookId,String title,int quantity){
        super(connection);
        this.bookID=bookId;
        this.title=title;
        this.quantity=quantity;
    }
    @Override
    public void execute(){
        try{
            String query="UPDATE books SET quantity=? WHERE id=? OR title=? ";
            java.sql.PreparedStatement pst=connection.prepareStatement(query);
            pst.setString(3, title);
            pst.setInt(1, quantity);
            pst.setInt(2, bookID);
            pst.executeUpdate();
            
            int rowsAffected=pst.executeUpdate();
            
            if(rowsAffected>0){
                JOptionPane.showMessageDialog(null,"Book updated successfully!");
            }
            else
               JOptionPane.showMessageDialog(null,"Book ID not found.");

            
        }
        catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null,"Error updating book: "+e.getMessage());
            
        }
    }
}
