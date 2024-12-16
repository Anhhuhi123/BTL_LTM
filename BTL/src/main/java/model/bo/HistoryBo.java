package model.bo;

import java.sql.SQLException;
import java.util.List;

import model.bean.HistoryBean;
import model.dao.HistoryDao;

public class HistoryBo {
    private HistoryDao historyDao = new HistoryDao();

    public boolean saveHistory(HistoryBean history) throws SQLException {
    	  return historyDao.insertHistory(history.getUserId(), history.getFilePdf(), history.getFileDocx());
    }

    public List<HistoryBean> showListHistory(int userId) throws Exception {
        return historyDao.getHistoryByUserId(userId);
    }
}

