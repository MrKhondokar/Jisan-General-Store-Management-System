import java.sql.*;
import java.util.Scanner;

public class JisanGeneralStore2 {
    static final String DB_URL = "jdbc:mysql://localhost:3306/jisangeneralstore_2";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n=== Jisan General Store Management ===");
                System.out.println("1. Add Supplier");
                System.out.println("2. Add Inventory");
                System.out.println("3. Add Customer");
                System.out.println("4. Add Employee");
                System.out.println("5. Add Promotion");
                System.out.println("6. Add Sale");
                System.out.println("7. Add Audit Log");
                System.out.println("8. Add Payment");
                System.out.println("9. Add Stock Alert");
                System.out.println("10. View Data");
                System.out.println("11. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> addSupplier(conn, scanner);
                    case 2 -> addInventory(conn, scanner);
                    case 3 -> addCustomer(conn, scanner);
                    case 4 -> addEmployee(conn, scanner);
                    case 5 -> addPromotion(conn, scanner);
                    case 6 -> addSale(conn, scanner);
                    case 7 -> addAuditLog(conn, scanner);
                    case 8 -> addPayment(conn, scanner);
                    case 9 -> addStockAlert(conn, scanner);
                    case 10 -> viewData(conn, scanner);
                    case 11 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addSupplier(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Supplier Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Balance Due: ");
        double balance = scanner.nextDouble();

        String query = "INSERT INTO Suppliers (supplier_name, contact, email, address, balance_due) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, contact);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setDouble(5, balance);
            stmt.executeUpdate();
            System.out.println("Supplier added successfully.");
        }
    }

    private static void addInventory(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Supplier ID: ");
        int supplierId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Price Per Unit: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
        String expiryDate = scanner.nextLine();
        System.out.print("Enter Stock Threshold: ");
        int threshold = scanner.nextInt();

        String query = "INSERT INTO Inventory (item_name, category, supplier_id, quantity, price_per_unit, expiry_date, stock_threshold) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setInt(3, supplierId);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, price);
            stmt.setDate(6, Date.valueOf(expiryDate));
            stmt.setInt(7, threshold);
            stmt.executeUpdate();
            System.out.println("Inventory item added successfully.");
        }
    }

    private static void addCustomer(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Loyalty Points: ");
        int points = scanner.nextInt();

        String query = "INSERT INTO Customers (customer_name, contact, loyalty_points) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, contact);
            stmt.setInt(3, points);
            stmt.executeUpdate();
            System.out.println("Customer added successfully.");
        }
    }

    private static void addEmployee(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Role: ");
        String role = scanner.nextLine();
        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();

        String query = "INSERT INTO Employees (employee_name, role, contact, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, role);
            stmt.setString(3, contact);
            stmt.setDouble(4, salary);
            stmt.executeUpdate();
            System.out.println("Employee added successfully.");
        }
    }

    private static void addPromotion(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Promotion Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Discount Percentage: ");
        double discount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        String query = "INSERT INTO Promotions (promo_name, discount_percentage, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, discount);
            stmt.setDate(3, Date.valueOf(startDate));
            stmt.setDate(4, Date.valueOf(endDate));
            stmt.executeUpdate();
            System.out.println("Promotion added successfully.");
        }
    }

    private static void addSale(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Item ID: ");
        int itemId = scanner.nextInt();
        System.out.print("Enter Customer ID (or 0 for none): ");
        int customerId = scanner.nextInt();
        System.out.print("Enter Employee ID: ");
        int employeeId = scanner.nextInt();
        System.out.print("Enter Quantity Sold: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter Total Amount: ");
        double totalAmount = scanner.nextDouble();
        System.out.print("Enter Discount: ");
        double discount = scanner.nextDouble();
        System.out.print("Enter Tax: ");
        double tax = scanner.nextDouble();
        System.out.print("Enter Net Amount: ");
        double netAmount = scanner.nextDouble();

        String query = "INSERT INTO Sales (item_id, customer_id, employee_id, quantity_sold, total_amount, discount, tax, net_amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, customerId == 0 ? null : customerId);
            stmt.setInt(3, employeeId);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, totalAmount);
            stmt.setDouble(6, discount);
            stmt.setDouble(7, tax);
            stmt.setDouble(8, netAmount);
            stmt.executeUpdate();
            System.out.println("Sale added successfully.");
        }
    }

    private static void addAuditLog(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter User Role: ");
        String role = scanner.nextLine();
        System.out.print("Enter Action: ");
        String action = scanner.nextLine();

        String query = "INSERT INTO AuditLogs (user_role, action) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, role);
            stmt.setString(2, action);
            stmt.executeUpdate();
            System.out.println("Audit log added successfully.");
        }
    }

    private static void addPayment(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Sale ID: ");
        int saleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Payment Method(Cash/Card/Mobile Banking): ");
        String paymentMethod = scanner.nextLine();
        System.out.print("Enter Amount Paid: ");
        double amountPaid = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
        String paymentDate = scanner.nextLine();

        String query = "INSERT INTO Payments (sale_id, payment_method, amount_paid, payment_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, saleId);
            stmt.setString(2, paymentMethod);
            stmt.setDouble(3, amountPaid);
            stmt.setDate(4, Date.valueOf(paymentDate));
            stmt.executeUpdate();
            System.out.println("Payment added successfully.");
        }
    }

    private static void addStockAlert(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Item ID: ");
        int itemId = scanner.nextInt();
        System.out.print("Enter Current Stock: ");
        int currentStock = scanner.nextInt();
        System.out.print("Enter Stock Threshold: ");
        int stockThreshold = scanner.nextInt();

        String query = "INSERT INTO StockAlerts (item_id, current_stock, stock_threshold) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.setInt(2, currentStock);
            stmt.setInt(3, stockThreshold);
            stmt.executeUpdate();
            System.out.println("Stock alert added successfully.");
        }
    }

    private static void viewData(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Enter table name to view data: ");
        String tableName = scanner.nextLine();

        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + ": " + rs.getString(i) + " | ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving data: " + e.getMessage());
        }
    }
}
