package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;

import model.bean.HistoryBean;
import model.bean.UserBean;
import model.bo.HistoryBo;
import model.bo.UserBo;


@WebServlet("/view/historyConvert")
public class HistoryConvertServlet extends HttpServlet {
	
	   @Override
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   if(request.getSession().getAttribute("email") != null) {
			// tạo session lưu trữ lịch sử mà user đó đã convert
			   	HistoryBo historyBo = new HistoryBo();
	           	List<HistoryBean> listHistory;
				try {
					listHistory = historyBo.showListHistory((int) request.getSession().getAttribute("userId"));
					request.getSession().setAttribute("listHistory", listHistory);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   request.getRequestDispatcher("/view/historyConvert.jsp").forward(request, response);
		   }
		   else {
			   request.getRequestDispatcher("/view/login.jsp").forward(request, response);
		   }
	   }
}
