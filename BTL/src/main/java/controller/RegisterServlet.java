package controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import javax.servlet.ServletException;

import model.bean.UserBean;
import model.bo.UserBo;


@WebServlet("/view/Register")
public class RegisterServlet extends HttpServlet {
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String fullname = request.getParameter("fullName");
    	String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println(fullname);
        System.out.println(email);
        System.out.println(password);
        
        UserBean user = new UserBean(fullname,email, password);
        UserBo userBo = new UserBo();
                
        System.out.println(user.getEmail());
        try {
        	boolean emailExists = userBo.checkEmailExists(user);
        	if (emailExists) {
                request.setAttribute("error", "emailExists");
                request.getRequestDispatcher("/view/signup.jsp").forward(request, response);
            } else {
            	boolean isValid = userBo.register(user);
                    if (isValid) {
                    	System.out.println("đăng kí thành công");
                        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
                    } else {
                        request.setAttribute("error", "Failer register");
                        request.getRequestDispatcher("/view/signup.jsp").forward(request, response);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred, please try again later.");
            request.getRequestDispatcher("/view/signup.jsp").forward(request, response);
        }
      }
}

