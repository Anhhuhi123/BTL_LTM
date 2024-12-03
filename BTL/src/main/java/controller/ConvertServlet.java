package controller;

import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;

import model.dao.DatabaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@WebServlet("/view/convert")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ConvertServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
	private static final Queue<String> fileQueue = new ConcurrentLinkedQueue<>();
	// qa nhớ đổi lại OUTPUT_DIRECTORY nếu chạy k đc nhá
	private static final String OUTPUT_DIRECTORY = "/Users/macbook/Desktop/TestConvert/Test_Convert/src/Convert/";
	// OUTPUT_DIRECTORY của tien
	//	private static final String OUTPUT_DIRECTORY = "C:\\Users\\nguye\\OneDrive\\Desktop\\ltm\\BTL_LTM\\BTL\\src\\main\\webapp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        StringBuilder jsonResponse = new StringBuilder();
        System.out.println("Nhận được tín hiệu từ client");

        File outputDir = new File(OUTPUT_DIRECTORY);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.append("{");
            jsonResponse.append("\"status\":\"error\",");
            jsonResponse.append("\"message\":\"Không thể tạo thư mục đầu ra.\"");
            jsonResponse.append("}");
            resp.getWriter().write(jsonResponse.toString());
            return;
        }

        try {
            StringBuilder filesReceived = new StringBuilder();
            for (Part part : req.getParts()) {
                if (part.getName().equals("file")) {
                    String fileName = extractFileName(part);
                    String filePath = OUTPUT_DIRECTORY + fileName;
                    part.write(filePath);
                    fileQueue.add(filePath);
                    DatabaseHandler.insertFile(fileName, filePath);
                    filesReceived.append(fileName).append(", ");
                }
            }
            processFiles();

            jsonResponse.append("{");
            jsonResponse.append("\"status\":\"success\",");
            jsonResponse.append("\"message\":\"Tải lên thành công\",");
            jsonResponse.append("\"files\":\"").append(filesReceived.toString().isEmpty() ? "No files uploaded" : filesReceived.toString()).append("\"");
            jsonResponse.append("}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.append("{");
            jsonResponse.append("\"status\":\"error\",");
            jsonResponse.append("\"message\":\"Lỗi khi xử lý upload: ").append(e.getMessage()).append("\"");
            jsonResponse.append("}");
        }

        resp.getWriter().write(jsonResponse.toString());
    }


//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("text/plain");
//        StringBuilder responseMessage = new StringBuilder();
//        
//        System.out.println("nhan dc tin hieu tu client");
//
//        // Đảm bảo thư mục đầu ra tồn tại
//        File outputDir = new File(OUTPUT_DIRECTORY);
//        if (!outputDir.exists() && !outputDir.mkdirs()) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("Không thể tạo thư mục đầu ra.");
//            return;
//        }
//
//        try {
//            // Xử lý từng file được upload
//            for (Part part : req.getParts()) {
//                if (part.getName().equals("file")) {
//                    // Lưu file vào thư mục tạm thời
//                    String fileName = extractFileName(part);
//                    String filePath = OUTPUT_DIRECTORY + fileName;
//                    part.write(filePath);
//
//                    // Thêm file vào hàng đợi
//                    fileQueue.add(filePath);
//                    DatabaseHandler.insertFile(fileName, filePath);
//                    responseMessage.append("File nhận: ").append(fileName).append("\n");
//                }
//            }
//
//            // Bắt đầu xử lý file
//            processFiles();
//
//            resp.getWriter().write(responseMessage.toString());
//        } catch (Exception e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            resp.getWriter().write("Lỗi khi xử lý upload: " + e.getMessage());
//        }
//    }

    private void processFiles() {
        while (!fileQueue.isEmpty()) {
            String filePath = fileQueue.poll();
            try {
                File pdfFile = new File(filePath);
                if (!pdfFile.exists()) {
                    throw new Exception("File PDF không tồn tại: " + filePath);
                }

                // Tải tài liệu PDF
                Document pdfDocument = new Document(filePath);

                // Cấu hình tùy chọn lưu
                DocSaveOptions saveOptions = new DocSaveOptions();
                saveOptions.setFormat(DocSaveOptions.DocFormat.Doc);
                saveOptions.setMode(DocSaveOptions.RecognitionMode.Flow);
                saveOptions.setRecognizeBullets(true);

                // Tạo tên file Word đầu ra
                String wordPath = OUTPUT_DIRECTORY + "Converted_" + System.currentTimeMillis() + "_" + pdfFile.getName().replace(".pdf", ".doc");

                // Chuyển đổi PDF sang Word
                pdfDocument.save(wordPath, saveOptions);

                System.out.println("Chuyển đổi thành công: " + wordPath);

                // Cập nhật trạng thái trong cơ sở dữ liệu
                DatabaseHandler.updateState(filePath, "SUCCESS");
                
            } catch (Exception e) {
                System.err.println("Lỗi khi xử lý file " + filePath + ": " + e.getMessage());
                DatabaseHandler.updateState(filePath, "FAILED");
            }
        }
    }	

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "unknown";
    }
}
