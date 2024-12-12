import java.sql.Connection;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
    DatabaseConnection dbConnection = new DatabaseConnection();
    Connection a=dbConnection.getConnection();
    int a=10;
}