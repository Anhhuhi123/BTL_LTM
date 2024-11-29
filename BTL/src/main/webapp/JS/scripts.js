// scripts.js

document.getElementById('file-input').addEventListener('change', handleFileSelection);
document.getElementById('submit-btn').addEventListener('click', startConversion);

let selectedFiles = [];
let processingFiles = [];

function handleFileSelection(event) {
    selectedFiles = Array.from(event.target.files);
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
    processingListElement.innerHTML = ''; // Clear previous processing list

    const successListElement = document.getElementById('success-list');
    const failedListElement = document.getElementById('failed-list');

    selectedFiles.forEach(file => {
        // Move file to processing
        const li = document.createElement('li');
        li.textContent = file.name;
        li.classList.add('processing');
        processingListElement.appendChild(li);
        
        // Simulate file processing (replace with actual conversion logic)
        simulateFileProcessing(file, li, successListElement, failedListElement);
    });
}

function simulateFileProcessing(file, processingItem, successListElement, failedListElement) {
    setTimeout(() => {
        // Simulating success or failure
        const isSuccess = Math.random() > 0.2; // 80% chance of success

        if (isSuccess) {
            processingItem.classList.remove('processing');
            processingItem.classList.add('success');
            successListElement.appendChild(processingItem);
        } else {
            processingItem.classList.remove('processing');
            processingItem.classList.add('failed');
            failedListElement.appendChild(processingItem);
        }
    }, Math.random() * 5000 + 1000); // Random processing time between 1-6 seconds
}
