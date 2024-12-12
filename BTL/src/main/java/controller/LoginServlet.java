package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.UserBean;
import model.bo.UserBo;


@WebServlet("/view/Login")
public class LoginServlet extends HttpServlet {

    @Override 
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserBean user = new UserBean(email, password);
        UserBo userBo = new UserBo();


        try {
            boolean isValid = userBo.login(user);
            if (isValid) {
                response.sendRedirect(request.getContextPath() + "/view/main.jsp");
            } else {
                request.setAttribute("error", "Incorrect information or password");
                request.getRequestDispatcher("/view/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           request.getRequestDispatcher("/view/login.jsp").forward(request, response);

    }
}

