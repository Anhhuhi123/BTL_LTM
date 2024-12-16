package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.HistoryBean;
import model.bean.UserBean;
import model.bo.HistoryBo;
import model.bo.UserBo;


@WebServlet("/view/logout")
public class LogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("userId");
		request.getSession().removeAttribute("fullname");
		request.getSession().removeAttribute("email");   
		response.sendRedirect(request.getContextPath() + "/view/login.jsp");
    }
}

