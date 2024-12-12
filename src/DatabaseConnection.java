import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/hotel_management12";
    private static final String USER = "root"; //
    private static final String PASSWORD = "2023"; //

    public  Connection getConnection() {
        Connection connection = null;
        try {
            // Load and register MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



}
