package controller;

import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;

import model.bo.ConvertThread;
import model.dao.DatabaseHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@WebServlet("/view/convert")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)

public class ConvertServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Queue<Part> fileQueue = new ConcurrentLinkedQueue<>();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        StringBuilder jsonResponse = new StringBuilder();
        System.out.println("Nhận được tín hiệu từ client");
        System.out.println("Nhận được tín hiệu từ client111");
        System.out.println(req.getParts());
        for (Part part : req.getParts()) {
            System.out.println("Submitted file name: " + part.getName());
        }

        try {
            // Xử lý file gửi từ client
            Queue<Part> fileQueue = new ConcurrentLinkedQueue<>();
            for (Part part : req.getParts()) {
                if (part.getName().equals("file")) {
                    fileQueue.add(part); // Thêm phần tử vào Queue
                }
            }

            if (!fileQueue.isEmpty()) {
                // Trường hợp 1: Chuyển đổi một file duy nhất
                if (fileQueue.size() == 1) {
                    Part part = fileQueue.poll(); // Lấy phần tử đầu tiên ra khỏi Queue
                    InputStream inputStream = part.getInputStream();
                    String fileName = extractFileName(part);
                    if (!fileName.endsWith(".pdf")) {
                        jsonResponse.append("{");
                        jsonResponse.append("\"status\":\"error\",");
                        jsonResponse.append("\"message\":\"Chỉ chấp nhận tệp PDF.\"");
                        jsonResponse.append("}");
                        resp.getWriter().write(jsonResponse.toString());
                        return;
                    }

                    // Chuyển đổi PDF sang Word trong bộ nhớ
                    ByteArrayOutputStream wordOutputStream = new ByteArrayOutputStream();
                    
                    processPdfToWord(inputStream, wordOutputStream);
                  
                    // Gửi file Word về cho client
                    resp.setContentType("application/msword");
                    resp.setHeader("Content-Disposition", "attachment; filename=" + fileName.replace(".pdf", ".doc"));
                    wordOutputStream.writeTo(resp.getOutputStream());
                    return;
                }

                // Trường hợp 2: Chuyển đổi nhiều file và gom chúng vào file ZIP
                else if (fileQueue.size() > 1) {
                    // Tạo một ByteArrayOutputStream để chứa ZIP
                    ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
                    try (ZipOutputStream zipStream = new ZipOutputStream(zipOutputStream)) {
                        while (!fileQueue.isEmpty()) {
                            Part part = fileQueue.poll(); // Lấy phần tử đầu tiên ra khỏi Queue
                            InputStream inputStream = part.getInputStream();
                            String fileName = extractFileName(part);
                            if (!fileName.endsWith(".pdf")) {
                                jsonResponse.append("{");
                                jsonResponse.append("\"status\":\"error\",");
                                jsonResponse.append("\"message\":\"Chỉ chấp nhận tệp PDF.\"");
                                jsonResponse.append("}");
                                resp.getWriter().write(jsonResponse.toString());
                                return;
                            }

                            // Chuyển đổi PDF sang Word và lưu vào ZIP
                            ByteArrayOutputStream wordOutputStream = new ByteArrayOutputStream();
                            processPdfToWord(inputStream, wordOutputStream);
                            zipStream.putNextEntry(new ZipEntry(fileName.replace(".pdf", ".doc")));
                            wordOutputStream.writeTo(zipStream);
                            zipStream.closeEntry();
                        }
                    }

                    // Gửi file ZIP về cho client
                    resp.setContentType("application/zip");
                    resp.setHeader("Content-Disposition", "attachment; filename=converted_files.zip");
                    zipOutputStream.writeTo(resp.getOutputStream());
                    return;
                }
            } else {
                // Trường hợp không có file nào được gửi
                jsonResponse.append("{");
                jsonResponse.append("\"status\":\"error\",");
                jsonResponse.append("\"message\":\"Không có tệp PDF nào được gửi.\"");
                jsonResponse.append("}");
                resp.getWriter().write(jsonResponse.toString());
                return;
            }
        } catch (Exception e) {
            jsonResponse.append("{");
            jsonResponse.append("\"status\":\"error\",");
            jsonResponse.append("\"message\":\"Lỗi khi xử lý: ").append(e.getMessage()).append("\"");
            jsonResponse.append("}");
        }

        resp.getWriter().write(jsonResponse.toString());
    }

    private void processPdfToWord(InputStream pdfInputStream, ByteArrayOutputStream wordOutputStream) throws Exception {
        // Tạo một tài liệu PDF từ InputStream
        Document pdfDocument = new Document(pdfInputStream);
        
        // Cấu hình tùy chọn lưu
        DocSaveOptions saveOptions = new DocSaveOptions();
        saveOptions.setFormat(DocSaveOptions.DocFormat.Doc);
        saveOptions.setMode(DocSaveOptions.RecognitionMode.Flow);
        saveOptions.setRecognizeBullets(true);
        
        // Lưu tài liệu Word vào ByteArrayOutputStream
        pdfDocument.save(wordOutputStream, saveOptions);
    }

    private String extractFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        System.out.println(contentDisposition);
        System.out.println("Submitted file name abcd" );
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                System.out.println("Submitted file name abcde" );
                return content.substring(content.indexOf("=") + 2, content.length() - 1);
            }
        }
        return "unknown";
    }
}



// @WebServlet("/view/convert")
// @MultipartConfig(
//     fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//     maxFileSize = 1024 * 1024 * 10, // 10MB
//     maxRequestSize = 1024 * 1024 * 50 // 50MB
// )
// public class ConvertServlet extends HttpServlet {

//     private static final long serialVersionUID = 1L;
//     private static final Queue<Part> fileQueue = new ConcurrentLinkedQueue<>();

//     @Override
//     protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//         resp.setContentType("application/json");
//         resp.setCharacterEncoding("UTF-8");

//         StringBuilder jsonResponse = new StringBuilder();
//         try {
//             // Xử lý file gửi từ client
//             for (Part part : req.getParts()) {
//                 if (part.getName().equals("file")) {
//                     fileQueue.add(part); // Thêm phần tử vào Queue
//                 }
//             }

//             if (!fileQueue.isEmpty()) {
//                 ArrayList<String> docFilePaths = new ArrayList<>();

//                 // Chuyển đổi từng file PDF sang DOCX
//                 while (!fileQueue.isEmpty()) {
//                     Part part = fileQueue.poll();
//                     String fileName = extractFileName(part);
//                     if (!fileName.endsWith(".pdf")) {
//                         jsonResponse.append("{");
//                         jsonResponse.append("\"status\":\"error\",");
//                         jsonResponse.append("\"message\":\"Chỉ chấp nhận tệp PDF.\"");
//                         jsonResponse.append("}");
//                         resp.getWriter().write(jsonResponse.toString());
//                         return;
//                     }

//                     // Tạo một thread để chuyển đổi file PDF sang DOCX
//                     ConvertThread convertThread = new ConvertThread(part.getInputStream(), docFilePaths, fileName);
//                     convertThread.start();
//                     convertThread.join(); // Chờ cho thread hoàn thành
//                 }

//                 // Tạo file ZIP chứa các file DOCX
//                 String zipFilePath = "output/converted_files.zip"; // Đường dẫn file ZIP
//                 try (FileOutputStream fos = new FileOutputStream(zipFilePath);
//                      ZipOutputStream zipOut = new ZipOutputStream(fos)) {
//                     for (String docFilePath : docFilePaths) {
//                         File docFile = new File(docFilePath);
//                         try (FileInputStream fis = new FileInputStream(docFile)) {
//                             ZipEntry zipEntry = new ZipEntry(docFile.getName());
//                             zipOut.putNextEntry(zipEntry);
//                             byte[] bytes = new byte[1024];
//                             int length;
//                             while ((length = fis.read(bytes)) >= 0) {
//                                 zipOut.write(bytes, 0, length);
//                             }
//                             zipOut.closeEntry();
//                         }
//                     }
//                 }

//                 // Gửi file ZIP về cho client
//                 resp.setContentType("application/zip");
//                 resp.setHeader("Content-Disposition", "attachment; filename=converted_files.zip");
//                 try (FileInputStream fis = new FileInputStream(zipFilePath)) {
//                     byte[] buffer = new byte[1024];
//                     int bytesRead;
//                     while ((bytesRead = fis.read(buffer)) != -1) {
//                         resp.getOutputStream().write(buffer, 0, bytesRead);
//                     }
//                 }
//                 return;
//             } else {
//                 jsonResponse.append("{");
//                 jsonResponse.append("\"status\":\"error\",");
//                 jsonResponse.append("\"message\":\"Không có tệp PDF nào được gửi.\"");
//                 jsonResponse.append("}");
//                 resp.getWriter().write(jsonResponse.toString());
//                 return;
//             }
//         } catch (Exception e) {
//             jsonResponse.append("{");
//             jsonResponse.append("\"status\":\"error\",");
//             jsonResponse.append("\"message\":\"Lỗi khi xử lý: ").append(e.getMessage()).append("\"");
//             jsonResponse.append("}");
//         }

//         resp.getWriter().write(jsonResponse.toString());
//     }

//     private String extractFileName(Part part) {
//         String contentDisposition = part.getHeader("content-disposition");
//         for (String content : contentDisposition.split(";")) {
//             if (content.trim().startsWith("filename")) {
//                 return content.substring(content.indexOf("=") + 2, content.length() - 1);
//             }
//         }
//         return "unknown";
//     }
// }