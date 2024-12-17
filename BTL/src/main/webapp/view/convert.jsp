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

    <script src="../JS/convert.js"></script>
</body>
</html>
