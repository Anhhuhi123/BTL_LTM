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
    let formData = new FormData();
    let li = document.createElement('li');
    selectedFiles.forEach(file => {
        // Tạo và thêm file vào danh sách "Processing"
        li.textContent = file.name;
        li.classList.add('processing');
        processingListElement.appendChild(li);
        
        // Chuẩn bị FormData
        formData.append("file", file);
        // Gọi hàm sendFileToServer nhưng hàm này chưa được định nghĩa!
        /*sendFileToServer(formData, file, li, successListElement, failedListElement);*/
    });
        
        sendFileToServer(formData, selectedFiles, li, successListElement, failedListElement);
}

/*function sendFileToServer(formData, file, processingItem, successList, failedList) {
   fetch('http://localhost:8080/BTL/view/convert', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            return response.blob(); // Lấy dữ liệu nhị phân nếu thành công
        } else {
			console.log('lỗi');
            // Nếu phản hồi không thành công, trả về JSON từ server để xử lý
            return response.json().then(err => {
				console.log(err);
                throw new Error(err.message || 'Chuyển đổi thất bại.');
            });
        }
    })
    .then(blob => {
		console.log(blob);
		processingItem.remove();
		const successItem = document.createElement('li');
        successItem.textContent = file.name;
        successList.appendChild(successItem);
        // Tải file Word về cho người dùng
        const link = document.createElement('a');
        const url = window.URL.createObjectURL(blob);
        link.href = url;
        link.download = file.name.replace(".pdf", ".doc");
        link.click();
        console.log('aaaaaaaaaaaa');
        window.URL.revokeObjectURL(url); // Giải phóng bộ nhớ
    })
    .catch(error => {
        // Xử lý lỗi
        console.error("Fetch error:", error.message);
        // Cập nhật giao diện hiển thị lỗi
        processingItem.remove();
        const failedItem = document.createElement('li');
        failedItem.textContent = `${error.message}`;
        failedList.appendChild(failedItem);
    });

}*/



function sendFileToServer(formData, selectedFiles, processingItem, successList, failedList) {
	console.log(formData);
    fetch('http://localhost:8080/BTL/view/convert', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            return response.blob(); // Lấy dữ liệu nhị phân nếu thành công
        } else {
            // Nếu phản hồi không thành công, trả về JSON từ server để xử lý
            return response.json().then(err => {
                throw new Error(err.message || 'Chuyển đổi thất bại.');
            });
        }
    })
    .then(blob => {
		if(selectedFiles.length === 1){
        // Xử lý blob trả về từ server
        console.log('Dữ liệu blob nhận được:', blob);

        // Loại bỏ item đang xử lý từ giao diện
        processingItem.remove();

        // Thêm thông tin file thành công vào danh sách
        const successItem = document.createElement('li');
        successItem.textContent = selectedFiles[0].name;
        successList.appendChild(successItem);

        // Tạo link tải file Word về cho người dùng
        const link = document.createElement('a');
        const url = window.URL.createObjectURL(blob);
        link.href = url;
        link.download = selectedFiles[0].name.replace(".pdf", ".doc"); // Đổi phần mở rộng file
        link.click();

        // Giải phóng bộ nhớ URL sau khi tải xong
        window.URL.revokeObjectURL(url);

        console.log('File đã được tải về thành công.');
		}
		else{
			  // Trường hợp có nhiều file, blob là một tệp ZIP
			console.log(blob);
	        const zipBlob = blob;  // blob chứa ZIP file
	
	        // Loại bỏ item đang xử lý từ giao diện
	        processingItem.remove();
	
	        // Thêm thông tin file ZIP thành công vào danh sách
	        const successItem = document.createElement('li');
	        successItem.textContent = 'Tải tệp ZIP đã chuyển đổi thành công';
	        successList.appendChild(successItem);
	
	        // Tạo link tải file ZIP về cho người dùng
	        const zipLink = document.createElement('a');
	        const zipUrl = window.URL.createObjectURL(zipBlob);
	        zipLink.href = zipUrl;
	        zipLink.download = 'converted_files.zip';  // Tên file ZIP
	        zipLink.click();
	
	        // Giải phóng bộ nhớ URL sau khi tải xong
	        window.URL.revokeObjectURL(zipUrl);
	
	        console.log('Tệp ZIP đã được tải về thành công.');
		}
    })
    .catch(error => {
        // Xử lý lỗi nếu có
        console.error('Fetch error:', error.message);

        // Loại bỏ item đang xử lý từ giao diện
        processingItem.remove();

        // Thêm thông tin lỗi vào danh sách lỗi
        const failedItem = document.createElement('li');
        failedItem.textContent = `${error.message}`;
        failedList.appendChild(failedItem);
    });
}










