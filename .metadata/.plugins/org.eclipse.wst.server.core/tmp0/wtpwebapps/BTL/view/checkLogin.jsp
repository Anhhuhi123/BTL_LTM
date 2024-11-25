<%@ page language="java" import="javax.servlet.RequestDispatcher" %>
<%@ page language="java" import="javax.servlet.ServletException" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet" %>

<%
    // Cấu hình database
    String DB_URL = "jdbc:mysql://localhost:3306/DULIEU"; // Đảm bảo URL đúng cú pháp
    String USER_NAME = "root"; // Thay thế bằng username của MySQL
    String PASSWORD = "";      // Thay thế bằng mật khẩu của MySQL

    // Lấy thông tin từ form
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Khởi tạo biến trạng thái
    boolean isAuthenticated = false;

    try {
        // Load driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        
        // Kết nối đến database
        try (Connection conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD)) {
            // Câu truy vấn có tham số
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        isAuthenticated = true; // Đã tìm thấy tài khoản phù hợp
                    }
                }
            }
        }
        System.out.print("ket noi thanh cong");
    } catch (Exception e) {
        // Ghi log lỗi cho developer
        e.printStackTrace(); // Ghi lỗi chi tiết vào log
        System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        System.out.print(e);
    }

    // Điều hướng theo kết quả xác thực
    if (isAuthenticated) {
        String address = "192 nguyen luong bang"; // Dữ liệu mẫu
        request.setAttribute("address", address);
        RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
        rd.forward(request, response);
    } else {
        response.sendRedirect("login.jsp");
    }
%>
