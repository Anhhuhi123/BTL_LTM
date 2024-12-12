<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>File Conversion Progress</title>
    <link href="../assets/ImgConvert.css" rel="stylesheet">
    
</head>
<body>
    <div class="convert-page">
        <div class="container">
            <h1>PDF to DOC Conversion</h1>
            
            <!-- File input -->
            <div class="file-upload">
                <label for="file-input">Choose Files:</label>
                <input type="file" id="file-input" multiple>
                <button id="submit-btn">Start Conversion</button>
            </div>

            <!-- Progress Columns -->
            <div class="progress-container">
                <!-- Column for uploaded files -->
                <div class="column" id="uploaded-files">
                    <h3>Uploaded Files</h3>
                    <ul id="file-list">
                        <!-- List of selected files will appear here -->
                    </ul>
                </div>

                <!-- Arrow for indicating progression -->
                <div class="arrow">
                    <span>&#8594;</span>
                </div>

                <!-- Column for files being processed -->
                <div class="column" id="processing-files">
                    <h3>Processing Files</h3>
                    <ul id="processing-list">
                        <!-- Files being processed -->
                    </ul>
                </div>

                <!-- Arrow for indicating progression -->
                <div class="arrow">
                    <span>&#8594;</span>
                </div>

                <!-- Column for successful files -->
                <div class="column" id="successful-files">
                    <h3>Success</h3>
                    <ul id="success-list">
                        <!-- Processed files will appear here -->
                    </ul>
                </div>

                <!-- Arrow for indicating progression -->
                <div class="arrow">
                    <span>&#8594;</span>
                </div>

                <!-- Column for failed files -->
                <div class="column" id="failed-files">
                    <h3>Failed</h3>
                    <ul id="failed-list">
                        <!-- Failed files will appear here -->
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- <script src="../JS/scripts.js"></script> -->
</body>
<script>
	const fileInputElement = document.getElementById('file-input');
	const btnConvertElement = document.getElementById('submit-btn');
	
	fileInputElement.onchange = (e)=>{
		
		const fileListElement = document.getElementById('file-list');
	    fileListElement.innerHTML = '';
	    
		const files = e.target.files;
	    if (files.length > 0) {
	        console.log("Danh sách file được chọn:");
	        for (let i = 0; i < files.length; i++) {
	        	const li = document.createElement('li');
	            li.textContent = files[i].name;
	            fileListElement.appendChild(li);
	            console.log(files[i]); // In tên từng file
	        }
	            
	    }
	}
	// Lắng nghe sự kiện click vào nút
	btnConvertElement.onclick = (e) => {
		// element process
		const processingListElement = document.getElementById('processing-list');
	    processingListElement.innerHTML = ''; 
	    const li = document.createElement('li');
	    li.textContent = "waiting"
	   	li.classList.add('processing');
	    processingListElement.appendChild(li);
	    // save file into formData	    
		const formData = new FormData();
	    const files = fileInputElement.files;
	    if(files.length > 0){
	    	 for (let i = 0; i < files.length; i++) {   	
	    		formData.append("file",files[i]);
		     }
	    }
	    sendFileToServer(formData);
	    
	};

 
 
 function sendFileToServer(formData) {
	    fetch("http://localhost:8080/BTL/view/convert", {
	        method: 'POST',
	        body: formData
	    })
	        .then((response) => {
	            if (response.ok) {
	                const contentDisposition = response.headers.get('Content-Disposition');
	                const fileName = contentDisposition
	                    ? contentDisposition.split('filename=')[1]
	                    : 'converted_file.zip';

	                return response.blob().then((blob) => {
	                    downloadFile(blob, fileName);
	                    updateSuccessColumn(fileName);
	                });
	            } else {
	                updateFailureColumn("Conversion failed. Please try again.");
	                throw new Error('Failed to convert files.');
	            }
	        })
	        .catch((error) => {
	            console.error("Error during file conversion:", error);
	        });
	}

	// Download the file
	function downloadFile(blob, fileName) {
	    const link = document.createElement('a');
	    link.href = window.URL.createObjectURL(blob);
	    link.download = fileName;
	    link.click();
	}

	// Update success column with file link
	function updateSuccessColumn(fileName) {
	    const successListElement = document.getElementById('success-list');
	    const li = document.createElement('li');
	    li.textContent = `File ready for download: ${fileName}`;
	    li.classList.add('success');
	    successListElement.appendChild(li);
	}

	// Update failure column with error message
	function updateFailureColumn(errorMessage) {
	    const failedListElement = document.getElementById('failed-list');
	    const li = document.createElement('li');
	    li.textContent = errorMessage;
	    li.classList.add('failure');
	    failedListElement.appendChild(li);
	}

</script>
</html>
