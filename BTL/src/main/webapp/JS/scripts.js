// scripts.js

document.getElementById('file-input').addEventListener('change', handleFileSelection);
document.getElementById('submit-btn').addEventListener('click', startConversion);

let selectedFiles = [];
let processingFiles = [];

function handleFileSelection(event) {
    selectedFiles = Array.from(event.target.files);
    /*console.log(selectedFiles);*/
    const fileListElement = document.getElementById('file-list');
    fileListElement.innerHTML = ''; // Clear previous list
	
    selectedFiles.forEach(file => {
        const li = document.createElement('li');
        li.textContent = file.name;
        fileListElement.appendChild(li);
    });
}

function startConversion() {
    const processingListElement = document.getElementById('processing-list');
    processingListElement.innerHTML = ''; // Xóa danh sách processing cũ

    const successListElement = document.getElementById('success-list');
    const failedListElement = document.getElementById('failed-list');

    const formData = new FormData();
    const li = document.createElement('li');
    
    selectedFiles.forEach(file => {
		console.log("file",file);
        // Tạo và thêm file vào danh sách "Processing"
        li.textContent = file.name;
        li.classList.add('processing');
        processingListElement.appendChild(li);
        
        // Chuẩn bị FormData
        formData.append("file", file);

        // Gọi hàm sendFileToServer nhưng hàm này chưa được định nghĩa!
    });
        sendFileToServer(formData, selectedFiles, li, successListElement, failedListElement);
}



function sendFileToServer(formData, file, processingItem, successList, failedList) {
	let contentType;
    fetch('http://localhost:8080/BTL/view/convert', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        
        // Kiểm tra Content-Type của phản hồi
        contentType = response.headers.get('Content-Type');
        
        if (!response.ok) {
            throw new Error('Server error');
        }
        
        // Kiểm tra xem có phải là tệp Word trả về không (loại bỏ charset)
        if (contentType && contentType.startsWith('application/msword')) {
            return response.blob(); // Dữ liệu tệp, trả về blob
        }
        if (contentType && contentType.startsWith('application/zip')) {
            return response.blob(); // Dữ liệu tệp, trả về blob
        }  
        else {
            return response.json(); // Dữ liệu JSON (thông báo lỗi hoặc thành công)
        }
    })
    .then(data => {
		console.log("data"+data);
        
		console.log(contentType);
        // Kiểm tra xem dữ liệu trả về là blob (tệp) hay JSON
        if (contentType && contentType.startsWith('application/msword')) {
            // Tạo URL tạm thời cho tệp và tải về
            console.log("success");
            const link = document.createElement('a');
            const fileName = file[0].name.replace(".pdf", ".doc"); // Đặt tên tệp
            link.href = URL.createObjectURL(data); // URL tạm thời cho blob
            link.download = fileName; // Tên tệp khi tải về
            link.click(); // Kích hoạt tải tệp
            console.log("File đã được tải về thành công.");
        }
        if (contentType && contentType.startsWith('application/zip')) {
            console.log("success");
		    const link = document.createElement('a'); // Tạo một thẻ <a>
		    
		    // URL tạm thời cho blob (tệp ZIP)
		    link.href = URL.createObjectURL(data); 
		    
		    // Đặt tên tệp cho khi tải về
		    link.download = "converted_files.zip"; 
		    
		    // Kích hoạt tải tệp
		    link.click(); 
		    
		    console.log("File đã được tải về thành công.");
        }  
        else {
            // Xử lý phản hồi JSON (thông báo kết quả)
            if (data.status === "success") {
                processingItem.remove();
                const successItem = document.createElement('li');
                successItem.textContent = file.name;
                successList.appendChild(successItem);
            } else {
                throw new Error(data.message);
            }
        }
    })
    .catch(error => {
        // Xử lý lỗi
        console.error("Fetch error:", error);
        processingItem.remove();
        const failedItem = document.createElement('li');
        failedItem.textContent = file.name;
        failedList.appendChild(failedItem);
    });
}




