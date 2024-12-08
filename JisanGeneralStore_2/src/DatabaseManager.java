import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JisanGeneralStore_2", "root", "");
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            throw e;
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed.");
        }
    }

    public void executeUpdate(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
}
