// JavaScript code extracted from convert.jsp

const fileInputElement = document.getElementById('file-input');
const btnConvertElement = document.getElementById('submit-btn');

fileInputElement.onchange = (e) => {
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
    li.textContent = "waiting";
    li.classList.add('processing');
    processingListElement.appendChild(li);
    // save file into formData
    const formData = new FormData();
    const files = fileInputElement.files;
    if (files.length > 0) {
        for (let i = 0; i < files.length; i++) {
            formData.append("file", files[i]);
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
