package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

import model.bo.ConverterThread;



@WebServlet("/view/convert")
@MultipartConfig
public class ConvertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ConvertServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Lấy tất cả các phần (parts) được gửi từ yêu cầu
		Collection<Part> parts = request.getParts();
		String filePathInServer = "";
		String fileNameInServer = "";
		String fileNameUserUpload = "";
		System.out.println("Nhận được dữ liệu");
		List<ConverterThread> threads = new ArrayList<>();
		List<String> filesNameInServer =  new ArrayList<>();
		for (Part part : parts) {
			// kiểm tra xem thử client gửi đúng file pdf chưa?
			String contentType = part.getContentType();
			if (!contentType.equals("application/pdf")) {
			    System.out.println("Tệp không hợp lệ: " + contentType);
			    continue;
			}
			
			// chỉ định đường dẫn tuyệt đối
			String folderUpload = request.getServletContext().getRealPath("/DemoUpload");

			Date now = new Date();
			String submittedFileName = part.getSubmittedFileName();
			if (submittedFileName == null || submittedFileName.isEmpty()) {
				System.out.println("Part này không phải file: " + part.getName());
				// Bỏ qua phần không phải file
			    continue;
			}
			// 	Lấy tên file (phần cuối của đường dẫn)
			fileNameUserUpload = Path.of(submittedFileName).getFileName().toString();

			fileNameInServer = now.getTime() + "_" + fileNameUserUpload;

			filesNameInServer.add(fileNameInServer);
			if (!Files.exists(Path.of(folderUpload))) {
				// tạo thư mục ở đường dẫn đã chỉ định
				Files.createDirectory(Path.of(folderUpload));
			}

			filePathInServer = folderUpload + "/" + fileNameInServer;
			// lưu data của file vào thư mục đã định nghĩa
			part.write(filePathInServer);

			System.out.println("Filename user upload: " + fileNameUserUpload);
			System.out.println("File upload in server: " + filePathInServer);

			ConverterThread thread = new ConverterThread(filePathInServer);
			threads.add(thread);
		    thread.start();
		}
		   // Chờ tất cả các luồng hoàn thành
		for (ConverterThread thread : threads) {
		    try {
		            thread.join();
		    } catch (InterruptedException e) {
		            e.printStackTrace();
		    }
		}
		sendFileConvertToClient(request,response,filesNameInServer);

	}

	private void sendFileConvertToClient(HttpServletRequest request, HttpServletResponse response, List<String> filesNameInServer) throws IOException {
	    // Kiểm tra xem có nhiều hơn 1 tệp hay không
	    if (filesNameInServer.size() > 1) {
	        // Đặt tên tệp ZIP
	        String zipFileName = "converted_files_" + System.currentTimeMillis() + ".zip";
	        String zipFilePath = request.getServletContext().getRealPath("/DemoUpload") + "/" + zipFileName;

	        // Tạo tệp ZIP và thêm các tệp vào đó
	        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
	             ZipOutputStream zos = new ZipOutputStream(fos)) {

	            // Duyệt qua các tệp trong danh sách
	            for (String fileNameInServer : filesNameInServer) {
	                String filePath = request.getServletContext().getRealPath("/DemoUpload") + "/" + fileNameInServer.replace(".pdf", ".docx");
	                File file = new File(filePath);

	                if (file.exists()) {
	                    try (FileInputStream fis = new FileInputStream(file)) {
	                        ZipEntry zipEntry = new ZipEntry(file.getName());
	                        zos.putNextEntry(zipEntry);

	                        byte[] buffer = new byte[4096];
	                        int bytesRead;
	                        while ((bytesRead = fis.read(buffer)) != -1) {
	                            zos.write(buffer, 0, bytesRead);
	                        }

	                        zos.closeEntry();
	                    }
	                }
	            }
	        }

	        // Gửi tệp ZIP tới client
	        sendFileToClient(zipFilePath, response, zipFileName);
	    } else {
	        // Nếu chỉ có một tệp, gửi tệp đó trực tiếp
	        String fileNameInServer = filesNameInServer.get(0).replace(".pdf", ".docx");
	        String filePath = request.getServletContext().getRealPath("/DemoUpload") + "/" + fileNameInServer;
	        sendFileToClient(filePath, response, fileNameInServer);
	    }
	}


	private void sendFileToClient(String filePath, HttpServletResponse response, String fileName) throws IOException {
	    File file = new File(filePath);

	    // Kiểm tra sự tồn tại của tệp
	    if (!file.exists()) {
	        response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found.");
	        return;
	    }

	    // Đặt Content-Type theo loại tệp
	    String contentType = getContentType(fileName);
	    response.setContentType(contentType);

	    // Thiết lập Header để tải tệp về
	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	    response.setContentLength((int) file.length());

	    // Đọc và gửi tệp cho client
	    try (FileInputStream fileInputStream = new FileInputStream(file);
	         OutputStream outputStream = response.getOutputStream()) {
	        byte[] buffer = new byte[4096];
	        int bytesRead;
	        long total = 0;

	        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
	            total += bytesRead;
	            outputStream.write(buffer, 0, bytesRead);
	        }

	        System.out.println("Tổng bytes đã gửi: " + total);
	    }
	}

	// Phương thức xác định loại tệp dựa trên phần mở rộng
	private String getContentType(String fileName) {
	    if (fileName.endsWith(".zip")) {
	        return "application/zip";
	    } else if (fileName.endsWith(".docx")) {
	        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	    } else {
	        return "application/octet-stream"; // Mặc định là loại tệp nhị phân
	    }
	}

}
