import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        InventoryManager inventoryManager = new InventoryManager(dbManager);
        SupplierManager supplierManager = new SupplierManager(dbManager);
        PaymentManager paymentManager = new PaymentManager(dbManager);

        try {
            dbManager.connect();
            Scanner scanner = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n--- Jisan General Store 2 ---");
                System.out.println("1. Add Item to Inventory");
                System.out.println("2. View Inventory");
                System.out.println("3. Add Supplier");
                System.out.println("4. View Suppliers");
                System.out.println("5. Record Payment");
                System.out.println("6. View Payments");
                System.out.println("7. Check Stock Alerts");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> inventoryManager.addItem();
                    case 2 -> inventoryManager.viewInventory();
                    case 3 -> supplierManager.addSupplier();
                    case 4 -> supplierManager.viewSuppliers();
                    case 5 -> paymentManager.recordPayment();
                    case 6 -> paymentManager.viewPayments();
                    case 7 -> inventoryManager.checkStockAlert();
                    case 8 -> System.out.println("Exiting application...");
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 8);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                dbManager.disconnect();
            } catch (Exception e) {
                System.out.println("Failed to disconnect: " + e.getMessage());
            }
        }
    }
}
