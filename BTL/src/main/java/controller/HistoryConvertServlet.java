package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.HistoryBean;
import model.bo.HistoryBo;


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
//				   response.sendRedirect(request.getContextPath() + "/view/historyConvert.jsp");
		   }
		   else {
			   request.getRequestDispatcher("/view/login.jsp").forward(request, response);
		   }
	   }
}
