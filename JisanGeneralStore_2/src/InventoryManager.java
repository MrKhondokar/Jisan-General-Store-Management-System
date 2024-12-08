import java.sql.*;
import java.util.Scanner;

public class InventoryManager {
    private final DatabaseManager dbManager;

    public InventoryManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public void addItem() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter item name: ");
            String name = scanner.nextLine();
            System.out.print("Enter category: ");
            String category = scanner.nextLine();
            System.out.print("Enter supplier ID: ");
            int supplierId = scanner.nextInt();
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            System.out.print("Enter price per unit: ");
            double price = scanner.nextDouble();
            System.out.print("Enter expiry date (YYYY-MM-DD): ");
            scanner.nextLine(); // Consume newline
            String expiryDate = scanner.nextLine();
            System.out.print("Enter stock threshold: ");
            int threshold = scanner.nextInt();

            String query = "INSERT INTO Inventory (item_name, category, supplier_id, quantity, price_per_unit, expiry_date, stock_threshold) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = dbManager.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setInt(3, supplierId);
            ps.setInt(4, quantity);
            ps.setDouble(5, price);
            ps.setDate(6, Date.valueOf(expiryDate));
            ps.setInt(7, threshold);
            ps.executeUpdate();

            System.out.println("Item added to inventory successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to add item: " + e.getMessage());
        }
    }

    public void viewInventory() {
        try {
            ResultSet resultSet = dbManager.executeQuery("SELECT * FROM Inventory");
            System.out.println("--- Inventory List ---");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("item_id")
                        + ", Name: " + resultSet.getString("item_name")
                        + ", Category: " + resultSet.getString("category")
                        + ", Quantity: " + resultSet.getInt("quantity")
                        + ", Price: " + resultSet.getDouble("price_per_unit")
                        + ", Expiry Date: " + resultSet.getDate("expiry_date")
                        + ", Stock Threshold: " + resultSet.getInt("stock_threshold"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch inventory: " + e.getMessage());
        }
    }

    public void checkStockAlert() {
        try {
            String query = "SELECT * FROM Inventory WHERE quantity <= stock_threshold";
            ResultSet rs = dbManager.executeQuery(query);
            System.out.println("--- Low Stock Alert ---");
            while (rs.next()) {
                System.out.println("Item: " + rs.getString("item_name") + ", Current Stock: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.out.println("Error checking stock alert: " + e.getMessage());
        }
    }
}