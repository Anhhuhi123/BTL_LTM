package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/DULIEU"; 
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "";     
	
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            // Đăng ký driver (không cần thiết với các JDBC driver hiện đại)
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL,USER_NAME , PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new SQLException("Error connecting to database: " + e.getMessage());
        }
        return connection;
    }

}
