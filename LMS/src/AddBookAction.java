
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.awt.HeadlessException;
import java.sql.*;
import javax.swing.JOptionPane;

public class AddBookAction extends AbstractAction {
    private String title;
    private String author;
    private String isbn;
    private int quantity;

    public AddBookAction(Connection connection, String title, String author, String isbn, int quantity) {
        super(connection);
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.quantity = quantity;
    }

    @Override
    public void execute() {
        try {
            int authorId = getAuthorId(author);

            if (authorId == -1) {
                authorId = insertAuthor(author);
            }

            String query = "INSERT INTO books (title, author_id, isbn, quantity) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, title);
            pst.setInt(2, authorId);
            pst.setString(3, isbn);
            pst.setInt(4, quantity);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(null, "Book added successfully!");

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding book: " + e.getMessage());
        }
    }

    private int getAuthorId(String authorName) throws SQLException {
        String query = "SELECT id FROM authors WHERE name = ?";
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, authorName);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("id");
        }
        
        return -1; 
    }

    private int insertAuthor(String authorName) throws SQLException {
        String query = "INSERT INTO authors (name) VALUES (?)";
        PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, authorName);
        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); 
        }

        throw new SQLException("Failed to retrieve author ID.");
    }
}

