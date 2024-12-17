package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/view/download")
@MultipartConfig
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DownloadServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 String action = request.getParameter("action");

		    if (action.equals("downloadfile")) {
		        String fileName = request.getParameter("fileName"); // Lấy tên file từ URL
		        String filePath = request.getServletContext().getRealPath("/DemoUpload") + "/" + fileName; // Đường dẫn file trên server

		        File file = new File(filePath);

		        // Kiểm tra file có tồn tại không
		        if (!file.exists()) {
		            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
		            return;
		        }

		        // Thiết lập Content-Type dựa trên loại file
		        String contentType = request.getServletContext().getMimeType(file.getName());
		        if (contentType == null) {
		            contentType = "application/octet-stream"; // Loại mặc định cho file nhị phân
		        }

		        response.setContentType(contentType);

		        // Thêm header để bắt buộc trình duyệt tải file xuống
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.substring(fileName.indexOf('_') + 1) + "\"");
		        response.setContentLength((int) file.length());

		        // Gửi file xuống client
		        try (FileInputStream inputStream = new FileInputStream(file);
		             OutputStream outputStream = response.getOutputStream()) {

		            byte[] buffer = new byte[4096];
		            int bytesRead;

		            while ((bytesRead = inputStream.read(buffer)) != -1) {
		                outputStream.write(buffer, 0, bytesRead);
		            }
		        }
		    } else {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
		    }
	}
}


