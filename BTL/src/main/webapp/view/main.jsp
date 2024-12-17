<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">	
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sidebar Example</title>

    <!-- Fonts -->
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&family=Poppins:wght@300;400;500;600&family=Raleway:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Bootstrap CSS & Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    
    <link href="../assets/Mainn.css" rel="stylesheet">
    
</head>
<body>
    <div class="wrapper">
        <header id="header" class="header">
            <i class="header-toggle d-xl-none bi bi-list"></i>

            <div class="profile-img">
                <img src="../assets/img/convertimg.png" alt="" class="img-fluid rounded-circle">
            </div>

            <a href="main.jsp" class="logo d-flex align-items-center justify-content-center">
                <h1 class="sitename">Convert</h1>
            </a>
	
            <div class="social-links text-center">
                <a class="twitter"><i class="bi bi-twitter-x"></i></a>
                <a class="facebook"><i class="bi bi-facebook"></i></a>
                <a class="instagram"><i class="bi bi-instagram"></i></a>
                <a class="google-plus"><i class="bi bi-skype"></i></a>
                <a class="linkedin"><i class="bi bi-linkedin"></i></a>
            </div>

            <nav id="navmenu" class="navmenu">
                <ul>
                    <li><a href="test.jsp" class="nav-link active"><i class="bi bi-house navicon"></i>Home</a></li>
                    <li><a href="./historyConvert" class="nav-link" data-page="historyConvert.jsp"><i class="bi bi-hdd-stack navicon"></i> History</a></li>
<!--                     <li><a href="#contact" class="nav-link"><i class="bi bi-gear navicon"></i> Setting</a></li>
 -->                    <li><a href="./login.jsp" class="nav-link"><i class="bi bi-box-arrow-right navicon"></i> Log out</a></li>
                </ul>
            </nav>
        </header>

        <main id="main" class="main">
            <div id="content-container" class="content-wrapper"></div>
        </main>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Main JavaScript -->
    <script src="../JS/main.js"></script>
    <!-- <script>
        $('.nav-link').click(function(e) {
            e.preventDefault();
            
            // Xóa lớp active khỏi tất cả các liên kết
            $('.nav-link').removeClass('active');
            // Thêm lớp active vào liên kết đã nhấn
            $(this).addClass('active');
            
            // Tải nội dung
            const page = $(this).data('page');
            loadContent(page);
            
            // Đóng menu di động nếu đang mở
            if (document.querySelector('.header-show')) {
                headerToggle();
            }
        });
    </script> -->
</body>
</html>