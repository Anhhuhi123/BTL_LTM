
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



//@WebServlet("/view/convert")
//@MultipartConfig(
//    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//    maxFileSize = 1024 * 1024 * 10, // 10MB
//    maxRequestSize = 1024 * 1024 * 50 // 50MB
//)
//public class ConvertServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//    private static final Queue<Part> fileQueue = new ConcurrentLinkedQueue<>();  // Thay vì lưu tên file, lưu luôn Part
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//
//        StringBuilder jsonResponse = new StringBuilder();
//        System.out.println("Nhận được tín hiệu từ client");
//
//        try {
//            // Lưu tất cả các file vào fileQueue
//            for (Part part : req.getParts()) {
//                if (part.getName().equals("file")) {
//                    fileQueue.offer(part);  // Đưa file vào Queue
//                }
//            }
//
//            // Xử lý từng file từ Queue
//            while (!fileQueue.isEmpty()) {
//                Part filePart = fileQueue.poll();  // Lấy file đầu tiên trong queue
//                String fileName = extractFileName(filePart);
//                if (!fileName.endsWith(".pdf")) {
//                    jsonResponse.append("{");
//                    jsonResponse.append("\"status\":\"error\",");
//                    jsonResponse.append("\"message\":\"File không đúng định dạng PDF.\"");
//                    jsonResponse.append("}");
//                    resp.getOutputStream().write(jsonResponse.toString().getBytes("UTF-8"));
//                    return;
//                }
//
//                // Chuyển đổi PDF sang Word
//                ByteArrayOutputStream wordOutput = convertPdfToWord(filePart);
//
//                // Thiết lập response để trả file Word về cho client
//                resp.setContentType("application/msword");
//                resp.setHeader("Content-Disposition", "attachment; filename=Converted_" + fileName.replace(".pdf", ".doc"));
//                resp.getOutputStream().write(wordOutput.toByteArray());
//                resp.getOutputStream().flush();
//                return; // Dừng lại sau khi gửi file cho client
//            }
//
//            // Nếu không có file nào
//            jsonResponse.append("{");
//            jsonResponse.append("\"status\":\"error\",");
//            jsonResponse.append("\"message\":\"Không có file nào được tải lên.\"");
//            jsonResponse.append("}");
//            resp.getOutputStream().write(jsonResponse.toString().getBytes("UTF-8"));
//
//        } catch (Exception e) {
//            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            jsonResponse.append("{");
//            jsonResponse.append("\"status\":\"error\",");
//            jsonResponse.append("\"message\":\"Lỗi khi xử lý upload: ").append(e.getMessage()).append("\"");
//            jsonResponse.append("}");
//            resp.getOutputStream().write(jsonResponse.toString().getBytes("UTF-8"));
//        }
//    }
//
//    private ByteArrayOutputStream convertPdfToWord(Part part) throws Exception {
//        // Đọc nội dung file PDF từ InputStream
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(part.getInputStream().readAllBytes());
//
//        // Tải tài liệu PDF từ InputStream
//        Document pdfDocument = new Document(inputStream);
//
//        // Cấu hình tùy chọn lưu
//        DocSaveOptions saveOptions = new DocSaveOptions();
//        saveOptions.setFormat(DocSaveOptions.DocFormat.Doc);
//        saveOptions.setMode(DocSaveOptions.RecognitionMode.Flow);
//        saveOptions.setRecognizeBullets(true);
//
//        // Chuyển đổi PDF sang Word và lưu vào ByteArrayOutputStream
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        pdfDocument.save(outputStream, saveOptions);
//
//        return outputStream;
//    }
//
//    private String extractFileName(Part part) {
//        String contentDisposition = part.getHeader("content-disposition");
//        for (String content : contentDisposition.split(";")) {
//            if (content.trim().startsWith("filename")) {
//                return content.substring(content.indexOf("=") + 2, content.length() - 1);
//            }
//        }
//        return "unknown";
//    }
//}


@WebServlet("/view/convert")
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 2, // 2MB
  maxFileSize = 1024 * 1024 * 10, // 10MB
  maxRequestSize = 1024 * 1024 * 50 // 50MB
)

public class ConvertServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Giả sử bạn có fileQueue chứa các file upload
        Queue<Part> fileQueue = getFilePartsFromRequest(req);  // Lấy các file upload từ request
        StringBuilder jsonResponse = new StringBuilder();
        System.out.println(req);
        // Kiểm tra số lượng file trong queue
        if (fileQueue.size() == 1) {
        	System.out.println("1 file");
            // Trường hợp chỉ có 1 file
            Part filePart = fileQueue.poll();  // Lấy file đầu tiên trong queue
            String fileName = extractFileName(filePart);
            
            if (!fileName.endsWith(".pdf")) {
                // Nếu file không phải PDF, trả về lỗi cho client
                jsonResponse.append("{");
                jsonResponse.append("\"status\":\"error\",");
                jsonResponse.append("\"message\":\"File không đúng định dạng PDF.\"");
                jsonResponse.append("}");
                resp.setContentType("application/json");
                resp.getOutputStream().write(jsonResponse.toString().getBytes("UTF-8"));
                return;
            }

            // Chuyển đổi PDF sang Word
            ByteArrayOutputStream wordOutput = convertPdfToWord(filePart);

            // Thiết lập response để trả file Word về cho client
            resp.setContentType("application/msword");
            resp.setHeader("Content-Disposition", "attachment; filename=" + fileName.replace(".pdf", ".doc"));
            resp.getOutputStream().write(wordOutput.toByteArray());
            resp.getOutputStream().flush();
            System.out.println("1111 file");
            
        } else {
            // Trường hợp có nhiều file
        	System.out.println("nhiều fiile");
            ByteArrayOutputStream zipByteArrayOutputStream = new ByteArrayOutputStream();
            ZipOutputStream zipOutputStream = new ZipOutputStream(zipByteArrayOutputStream);

            while (!fileQueue.isEmpty()) {
                Part filePart = fileQueue.poll();  // Lấy file đầu tiên trong queue
                String fileName = extractFileName(filePart);

                if (!fileName.endsWith(".pdf")) {
                    // Nếu có file không phải PDF, trả về lỗi cho client
                    jsonResponse.append("{");
                    jsonResponse.append("\"status\":\"error\",");
                    jsonResponse.append("\"message\":\"File không đúng định dạng PDF.\"");
                    jsonResponse.append("}");
                    resp.setContentType("application/json");
                    resp.getOutputStream().write(jsonResponse.toString().getBytes("UTF-8"));
                    return;
                }

                // Chuyển đổi PDF sang Word
                ByteArrayOutputStream wordOutput = convertPdfToWord(filePart);

                // Thêm file vào trong ZIP
                ZipEntry zipEntry = new ZipEntry(fileName.replace(".pdf", ".doc"));
                zipOutputStream.putNextEntry(zipEntry);
                zipOutputStream.write(wordOutput.toByteArray());
                zipOutputStream.closeEntry();
                
                // Đóng và gửi file ZIP về client
                
                
                // Gửi tệp ZIP ra cho client
            }
            // Thiết lập các header để trả về tệp ZIP
            zipOutputStream.close();  // Đóng ZipOutputStream
            resp.setContentType("application/zip");
            resp.setHeader("Content-Disposition", "attachment; filename=converted_files.zip");
            resp.getOutputStream().write(zipByteArrayOutputStream.toByteArray());
            resp.getOutputStream().flush();
            System.out.println("nhiều 2222");

        }
    }

    private Queue<Part> getFilePartsFromRequest(HttpServletRequest req) throws IOException, ServletException {
        // Tạo queue để chứa các file upload
        Queue<Part> fileQueue = new LinkedList<>();
        for (Part part : req.getParts()) {
            if (part.getName().startsWith("file")) {  // Kiểm tra nếu là file
                fileQueue.add(part);
            }
        }
        return fileQueue;
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }

    private ByteArrayOutputStream convertPdfToWord(Part filePart) throws IOException {
        // Chuyển đổi PDF sang Word (cần thay bằng logic của bạn)
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // Giả sử bạn có một phương thức chuyển đổi PDF sang Word và ghi vào outputStream
        // convertPdfToWordLogic(filePart.getInputStream(), outputStream);
        return outputStream;
    }
}







