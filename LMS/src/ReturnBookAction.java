import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ReturnBookAction extends AbstractAction {
    private int userId;
    private String title;

    public ReturnBookAction(Connection connection, int userId, String title) {
        super(connection);
        this.userId = userId;
        this.title = title;
    }

    @Override
    public void execute() {
        try {
            String findBookQuery = "SELECT id FROM books WHERE title = ?";
            PreparedStatement findBookPst = connection.prepareStatement(findBookQuery);
            findBookPst.setString(1, title);
            ResultSet bookResultSet = findBookPst.executeQuery();

            if (bookResultSet.next()) {
                int bookId = bookResultSet.getInt("id");

                String updateBorrowQuery = "UPDATE borrows SET return_date = NOW() WHERE user_id = ? AND book_id = ? AND return_date IS NULL";
                PreparedStatement updateBorrowPst = connection.prepareStatement(updateBorrowQuery);
                updateBorrowPst.setInt(1, userId);
                updateBorrowPst.setInt(2, bookId);
                updateBorrowPst.executeUpdate();

                String updateQuantityQuery = "UPDATE books SET quantity = quantity + 1 WHERE id = ?";
                PreparedStatement updateQuantityPst = connection.prepareStatement(updateQuantityQuery);
                updateQuantityPst.setInt(1, bookId);
                updateQuantityPst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No book found with the title: " + title);
            }

            bookResultSet.close();
            findBookPst.close();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error returning book: " + e.getMessage());
        }
    }
}
