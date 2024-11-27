package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.ServletException;

import model.bean.UserBean;
import model.bo.UserBo;


@WebServlet("/view/Login")
public class LoginServlet extends HttpServlet {
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        System.out.println(email);
        System.out.println(password);

        UserBean user = new UserBean(email, password);
        UserBo userBo = new UserBo();
                
        System.out.println(user.getEmail());

        try {
            boolean isValid = userBo.login(user);
            if (isValid) {
            	System.out.println("thong tin dang nhap hop le");
                response.sendRedirect(request.getContextPath() + "/view/welcome.jsp");
            } else {
                request.setAttribute("error", "Thông tin đăng nhập không hợp lệ!");
                request.getRequestDispatcher("/view/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      }

}

