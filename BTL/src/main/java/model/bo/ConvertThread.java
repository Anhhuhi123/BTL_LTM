package model.bo;

public class ConvertThread extends Thread {
	private String filePath;

	public ConvertThread(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void run() {
		PdfConvertionHelper.convertPdfToDoc(filePath);
	}
}

// package model.bo;

// import java.io.InputStream;
// import java.util.ArrayList;

// public class ConvertThread extends Thread {
//     private InputStream inputStream;
//     private ArrayList<String> docFilePaths;
//     private String fileName;

//     public ConvertThread(InputStream inputStream, ArrayList<String> docFilePaths, String fileName) {
//         this.inputStream = inputStream;
//         this.docFilePaths = docFilePaths;
//         this.fileName = fileName;
//     }

//     @Override
//     public void run() {
//         try {
//             // Gọi hàm chuyển đổi PDF sang DOCX
//             String docFilePath = PdfConvertionHelper.convertPdfToDoc(inputStream, fileName);
//             synchronized (docFilePaths) {
//                 docFilePaths.add(docFilePath); // Thêm đường dẫn file DOCX vào danh sách
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }