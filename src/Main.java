import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Get connection to the database
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection connection = dbConnection.getConnection();

        // Example of using the connection
        if (connection != null) {
            System.out.println("Connection established successfully!");
            // You can now use the connection for queries, etc.
        } else {
            System.out.println("Failed to establish connection.");
        }

        // Close the connection when done
        DatabaseConnection.closeConnection();
    }
}
