// package model.dao;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;

// import config.DatabaseConnection;


// public class DatabaseHandler {
//     public static void insertFile(String fileName, String filePath) {
//         String sql = "INSERT INTO files (file_name, file_path, state) VALUES (?, ?, 'PENDING')";
//         try (Connection conn = DatabaseConnection.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, fileName);
//             stmt.setString(2, filePath);
//             stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public static void updateState(String filePath, String state) {
//         String sql = "UPDATE files SET state = ? WHERE file_path = ?";
//         try (Connection conn = DatabaseConnection.getConnection();
//              PreparedStatement stmt = conn.prepareStatement(sql)) {
//             stmt.setString(1, state);
//             stmt.setString(2, filePath);
//             stmt.executeUpdate();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }
// }