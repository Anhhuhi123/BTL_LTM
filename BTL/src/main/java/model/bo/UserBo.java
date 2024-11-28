package model.bo;

import java.sql.SQLException;

import model.bean.UserBean;
import model.dao.UserDao;

public class UserBo {
    private UserDao userDao = new UserDao();

    public boolean login(UserBean user) throws SQLException {
        // Kiểm tra dữ liệu đầu vào
        if (user.getEmail() == null || user.getPassword() == null) {
            return false;
        }
        return userDao.validateUser(user.getEmail(), user.getPassword());
    }
    
    public boolean register(UserBean user) throws SQLException {
    	  return userDao.registerUser(user.getFullname(), user.getEmail(), user.getPassword());
    }
    
    public boolean checkEmailExists(UserBean user) throws Exception {
        return userDao.isEmailExists(user.getEmail());
    }
}

