import java.sql.*;
import java.util.Scanner;

public class SupplierManager {
    private final DatabaseManager dbManager;

    public SupplierManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void addSupplier() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter supplier name: ");
            String name = scanner.nextLine();
            System.out.print("Enter contact number: ");
            String contact = scanner.nextLine();
            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            System.out.print("Enter address: ");
            String address = scanner.nextLine();
            System.out.print("Enter initial balance due: ");
            double balanceDue = scanner.nextDouble();

            String query = "INSERT INTO Suppliers (supplier_name, contact, email, address, balance_due) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = dbManager.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, contact);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setDouble(5, balanceDue);
            ps.executeUpdate();

            System.out.println("Supplier added successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to add supplier: " + e.getMessage());
        }
    }

    public void viewSuppliers() {
        try {
            ResultSet rs = dbManager.executeQuery("SELECT * FROM Suppliers");
            System.out.println("--- Supplier List ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("supplier_id")
                        + ", Name: " + rs.getString("supplier_name")
                        + ", Contact: " + rs.getString("contact")
                        + ", Email: " + rs.getString("email")
                        + ", Address: " + rs.getString("address")
                        + ", Balance Due: " + rs.getDouble("balance_due"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch suppliers: " + e.getMessage());
        }
    }
}