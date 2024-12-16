package controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.HistoryBean;
import model.bean.UserBean;
import model.bo.ConverterThread;
import model.bo.HistoryBo;
import model.bo.UserBo;



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
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
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
	

