package model.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

// chú thích hàm chuyển đổi chia file thành nhiều file nhỏ
public class PdfConvertionHelper {
	// 10 page
	private static final int MAX_PAGES_PER_FILE = 10;

	public static void convertPdfToDoc(String fileInput) {
		try {
			File f = new File(fileInput);
			if (f.exists()) {
				String fileOutput = fileInput.replace(".pdf", ".docx");

				System.out.println("fileInput"+fileInput);
				System.out.println("fileOutput"+fileOutput);


				convertPdfToDoc(fileInput, fileOutput);
			}
		} catch (Exception e) {

		}
	}

	private static void convertPdfToDoc(String fileInput, String fileOutput) {
		ArrayList<String> pathOfChunkFiles = splitPdf(fileInput);

		System.out.println("pathOfChunkFiles"+pathOfChunkFiles);

		ArrayList<String> fileDocxPaths = convertChunkPdfToDocx(pathOfChunkFiles);
		Collections.sort(fileDocxPaths);
		CombineDocx.combineFiles(fileDocxPaths, fileOutput);
	}

	/**
	 * Convert file pdf thành các file pdf nhỏ hơn mà mỗi file chứa tối đa 10 trang
	 * @param filePath Đường dẫn file đầu vào
	 * @return ArrayList<String>: đường dẫn của các file pdf được chunk ra
	 */
	private static ArrayList<String> splitPdf(String filePath) {
		ArrayList<String> pathOfChunkFiles = new ArrayList<>();
		try {
			String fileNameDontHaveExtension = filePath.replace(".pdf", "").replaceAll(" ", "");

			System.out.println("fileNameDontHaveExtension"+fileNameDontHaveExtension);
			PDDocument document = PDDocument.load(new File(filePath));
			int totalPages = document.getNumberOfPages();
			System.out.println("totalPages"+totalPages);

			int fileIndex = 1;

			for (int start = 0; start < totalPages; start += MAX_PAGES_PER_FILE) {
				int end = Math.min(fileIndex * MAX_PAGES_PER_FILE, totalPages);

				PDDocument chunkDocument = new PDDocument();
				for (int page = start; page < end; page++) {
					chunkDocument.addPage(document.getPage(page));
				}

				String outputPdf = fileNameDontHaveExtension + "_part_" + fileIndex + ".pdf";
				pathOfChunkFiles.add(outputPdf);
				chunkDocument.save(outputPdf);
				chunkDocument.close();
				fileIndex++;
			}
			document.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return pathOfChunkFiles;
	}

	private static ArrayList<String> convertChunkPdfToDocx(ArrayList<String> chunkFiles) {
		CountDownLatch latch = new CountDownLatch(chunkFiles.size());
		ArrayList<String> docFilePaths = new ArrayList<>();
		try {
			ArrayList<ConvertDocxThread> threads = new ArrayList<>();
			for (String filePath : chunkFiles) {
				ConvertDocxThread thread = new ConvertDocxThread(docFilePaths, filePath, latch);
				threads.add(thread);
				thread.start();
			}

			// Sử dụng CountDownLatch để đợi tất cả các luồng hoàn thành mới thực hiện tiếp
			latch.await();
			System.out.println("Convert to sub docx files done, combining them ...");
		} catch (Exception e) {
		}
		return docFilePaths;
	}
}

class ConvertDocxThread extends Thread {
	private final CountDownLatch latch;
	private ArrayList<String> docFilePaths;
	private String filePath;

	public ConvertDocxThread(ArrayList<String> docFilePaths, String filePath, CountDownLatch latch) {
		this.filePath = filePath;
		this.docFilePaths = docFilePaths;
		this.latch = latch;
	}

	private void convert(String filePath) {
		try {
			System.out.println(filePath + " start");
			PdfDocument document = new PdfDocument();
			document.loadFromFile(filePath);
			String docFilePath = filePath.replace(".pdf", ".docx");
			document.saveToFile(docFilePath, FileFormat.DOCX);
			document.close();
			this.docFilePaths.add(docFilePath);
			System.out.println(filePath + " end");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			latch.countDown();
		}
	}

	@Override
	public void run() {
		this.convert(filePath);
	}
}



// public class PdfConvertionHelper {
//     private static final int MAX_PAGES_PER_FILE = 10;

//     public static String convertPdfToDoc(InputStream inputStream, String originalFileName) {
//         try {
//             // Tạo file tạm để lưu DOCX
//             String fileOutput = originalFileName.replace(".pdf", ".docx");
//             File tempFile = new File(fileOutput);
//             FileOutputStream outputStream = new FileOutputStream(tempFile);

//             // Chuyển đổi PDF sang DOCX
//             ArrayList<String> pathOfChunkFiles = splitPdf(inputStream, originalFileName);
//             ArrayList<String> fileDocxPaths = convertChunkPdfToDocx(pathOfChunkFiles);
//             Collections.sort(fileDocxPaths);
//             CombineDocx.combineFiles(fileDocxPaths, tempFile.getAbsolutePath());

//             outputStream.close();
//             return tempFile.getAbsolutePath(); // Trả về đường dẫn file DOCX
//         } catch (Exception e) {
//             e.printStackTrace();
//             return null;
//         }
//     }

//     private static ArrayList<String> splitPdf(InputStream inputStream, String originalFileName) {
//         ArrayList<String> pathOfChunkFiles = new ArrayList<>();
//         try {
//             PDDocument document = PDDocument.load(inputStream);
//             int totalPages = document.getNumberOfPages();
//             int fileIndex = 1;

//             for (int start = 0; start < totalPages; start += MAX_PAGES_PER_FILE) {
//                 int end = Math.min(fileIndex * MAX_PAGES_PER_FILE, totalPages);
//                 PDDocument chunkDocument = new PDDocument();
//                 for (int page = start; page < end; page++) {
//                     chunkDocument.addPage(document.getPage(page));
//                 }

//                 String outputPdf = originalFileName.replace(".pdf", "_part_" + fileIndex + ".pdf");
//                 pathOfChunkFiles.add(outputPdf);
//                 chunkDocument.save(outputPdf);
//                 chunkDocument.close();
//                 fileIndex++;
//             }
//             document.close();
//         } catch (Exception e) {
//             System.out.println(e.getMessage());
//         }
//         return pathOfChunkFiles;
//     }

//     private static ArrayList<String> convertChunkPdfToDocx(ArrayList<String> chunkFiles) {
//         CountDownLatch latch = new CountDownLatch(chunkFiles.size());
//         ArrayList<String> docFilePaths = new ArrayList<>();
//         try {
//             ArrayList<ConvertDocxThread> threads = new ArrayList<>();
//             for (String filePath : chunkFiles) {
//                 ConvertDocxThread thread = new ConvertDocxThread(docFilePaths, filePath, latch);
//                 threads.add(thread);
//                 thread.start();
//             }

//             latch.await();
//             System.out.println("Convert to sub docx files done, combining them ...");
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return docFilePaths;
//     }
// }

