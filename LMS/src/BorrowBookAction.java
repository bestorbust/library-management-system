import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class BorrowBookAction extends AbstractAction {
    private int userId;
    private String title;

    public BorrowBookAction(Connection connection, int userId, String title) {
        super(connection);
        this.userId = userId;
        this.title = title;
    }

    @Override
    public void execute() {
        try {
            String checkStockQuery = "SELECT id, quantity FROM books WHERE title = ?";
            PreparedStatement checkStockPst = connection.prepareStatement(checkStockQuery);
            checkStockPst.setString(1, title);
            ResultSet stockResultSet = checkStockPst.executeQuery();

            if (stockResultSet.next()) {
                int bookId = stockResultSet.getInt("id");
                int quantity = stockResultSet.getInt("quantity");

                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "The book \"" + title + "\" is currently out of stock.");
                } else {
                    String borrowQuery = "INSERT INTO borrows (user_id, book_id, borrow_date, due_date) VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 14 DAY))";
                    PreparedStatement borrowPst = connection.prepareStatement(borrowQuery);
                    borrowPst.setInt(1, userId);
                    borrowPst.setInt(2, bookId);
                    borrowPst.executeUpdate();

                    String updateQuantityQuery = "UPDATE books SET quantity = quantity - 1 WHERE id = ?";
                    PreparedStatement updateQuantityPst = connection.prepareStatement(updateQuantityQuery);
                    updateQuantityPst.setInt(1, bookId);
                    updateQuantityPst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No book found with the title: " + title);
            }

            stockResultSet.close();
            checkStockPst.close();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error borrowing book: " + e.getMessage());
        }
    }
}
