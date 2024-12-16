package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.bean.UserBean;


public class UserDao {
    public UserBean validateUser(String email, String password) {
    	UserBean user = new UserBean();
    	user = null;
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
             	int id = resultSet.getInt("id");
	            String fullname = resultSet.getString("fullname");
	            user = new UserBean(id, fullname, email, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean isEmailExists(String email) {
    	boolean isValid = false;
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try(Connection connection = DatabaseConnection.getConnection();
        	PreparedStatement preparedStatement = connection.prepareStatement(query)){
        	preparedStatement.setString(1, email);
        	 ResultSet resultSet = preparedStatement.executeQuery();
        	 if (resultSet.next()) {
                 int count = resultSet.getInt(1);
                 isValid = count > 0;
             }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return isValid;

    }
    
    public boolean registerUser(String fullname, String email, String password) {
        boolean isValid = false;
        String query = "INSERT INTO user (fullname, email, password) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
        		PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, fullname);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

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
