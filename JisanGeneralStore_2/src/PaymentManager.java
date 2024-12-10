import java.sql.*;
import java.util.Scanner;

public class PaymentManager {
    private final DatabaseManager dbManager;

    public PaymentManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void recordPayment() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter Sale ID: ");
            int saleId = scanner.nextInt();
            System.out.print("Enter Payment Method (Cash/Card/Mobile): ");
            scanner.nextLine(); // Consume newline
            String paymentMethod = scanner.nextLine();
            System.out.print("Enter Amount Paid: ");
            double amountPaid = scanner.nextDouble();

            String query = "INSERT INTO Payments (sale_id, payment_method, amount_paid) VALUES (?, ?, ?)";
            PreparedStatement ps = dbManager.prepareStatement(query);
            ps.setInt(1, saleId);
            ps.setString(2, paymentMethod);
            ps.setDouble(3, amountPaid);
            ps.executeUpdate();

            System.out.println("Payment recorded successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to record payment: " + e.getMessage());
        }
    }

    public void viewPayments() {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT * FROM Payments");
            System.out.println("--- Payment Records ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("payment_id")
                        + ", Sale ID: " + rs.getInt("sale_id")
                        + ", Method: " + rs.getString("payment_method")
                        + ", Amount Paid: " + rs.getDouble("amount_paid")
                        + ", Date: " + rs.getTimestamp("payment_date"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch payment records: " + e.getMessage());
        }
    }
}