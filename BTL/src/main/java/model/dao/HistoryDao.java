package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.bean.HistoryBean;

public class HistoryDao {
	  public List<HistoryBean> getHistoryByUserId(int userId) {
	  List<HistoryBean> listHistory = new ArrayList<>();
	  String query = "SELECT * FROM history WHERE userId = ?";
	
	  try (Connection connection = DatabaseConnection.getConnection();
	      PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	      preparedStatement.setInt(1, userId);
	
	      try (ResultSet resultSet = preparedStatement.executeQuery()) {
	          while (resultSet.next()) {
	        	  	 int id = resultSet.getInt("id");
	                 String filePdf = resultSet.getString("filePdf");
	                 String fileDocx = resultSet.getString("fileDocx");
	                 String data = resultSet.getString("date");
	                 // Thêm nhân viên vào danh sách
	                 listHistory.add(new HistoryBean(id, userId, filePdf, fileDocx,data));
	          }
	      }
	  } catch (SQLException e) {
	      e.printStackTrace();
	  }
	
	  return listHistory;
	}

    public boolean insertHistory(int userId	, String filePdf ,String fileDocx) {
        boolean isValid = false;
        String query = "INSERT INTO history (userId, filePdf, fileDocx) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, filePdf);
            preparedStatement.setString(3, fileDocx);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }


}
