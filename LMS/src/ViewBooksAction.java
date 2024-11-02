/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewBooksAction extends AbstractAction {
    private DefaultTableModel tableModel;

    public ViewBooksAction(Connection connection) {
        super(connection);
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Title", "Author", "ISBN", "Quantity"});
    }

    public void execute() {
        String query = "SELECT books.id, books.title, authors.name AS author, books.isbn, books.quantity " +
                       "FROM books " +
                       "JOIN authors ON books.author_id = authors.id";

        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Object[] rowData = {
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getInt("quantity")
                };
                tableModel.addRow(rowData);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loading books: " + e.getMessage());
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
