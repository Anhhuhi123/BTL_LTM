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

    selectedFiles.forEach(file => {
		console.log("aaa",file);
        // Tạo và thêm file vào danh sách "Processing"
        const li = document.createElement('li');
        li.textContent = file.name;
        li.classList.add('processing');
        processingListElement.appendChild(li);
        
        // Chuẩn bị FormData
        const formData = new FormData();
        formData.append("file", file);

        // Gọi hàm sendFileToServer nhưng hàm này chưa được định nghĩa!
        sendFileToServer(formData, file, li, successListElement, failedListElement);
    });
}

/*function sendFileToServer(formData, file, processingItem, successList, failedList) {
    fetch('http://localhost:8080/BTL/view/convert', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Server error');
        }
        return response.json();
    })
    .then(data => {
        // Xử lý thành công
        processingItem.remove();
        const successItem = document.createElement('li');
        successItem.textContent = file.name;
        successList.appendChild(successItem);
    })
    .catch(error => {
        // Xử lý thất bại
        console.error("Fetch error:", error);
        processingItem.remove();
        const failedItem = document.createElement('li');
        failedItem.textContent = file.name;
        failedList.appendChild(failedItem);
    });
}*/


function sendFileToServer(formData, file, processingItem, successList, failedList) {
    fetch('http://localhost:8080/BTL/view/convert', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Server error');
        }
        return response.json(); // Phản hồi JSON từ server
    })
    .then(data => {
        // Xử lý thành công
        console.log("Server response:", data);
        if (data.status === "success") {
            processingItem.remove();
            const successItem = document.createElement('li');
            successItem.textContent = file.name;
            successList.appendChild(successItem);
        } else {
            throw new Error(data.message);
        }
    })
    .catch(error => {
        // Xử lý thất bại
        console.error("Fetch error:", error);
        processingItem.remove();
        const failedItem = document.createElement('li');
        failedItem.textContent = file.name;
        failedList.appendChild(failedItem);
    });
}






