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
<!--                 <a href="#" class="twitter"><i class="bi bi-twitter-x"></i></a>
                <a href="#" class="facebook"><i class="bi bi-facebook"></i></a>
                <a href="#" class="instagram"><i class="bi bi-instagram"></i></a>
                <a href="#" class="google-plus"><i class="bi bi-skype"></i></a>
                <a href="#" class="linkedin"><i class="bi bi-linkedin"></i></a> -->
                
                <a  class="twitter"><i class="bi bi-twitter-x"></i></a>
                <a  class="facebook"><i class="bi bi-facebook"></i></a>
                <a  class="instagram"><i class="bi bi-instagram"></i></a>
                <a  class="google-plus"><i class="bi bi-skype"></i></a>
                <a  class="linkedin"><i class="bi bi-linkedin"></i></a>
            </div>

            <nav id="navmenu" class="navmenu">
                <ul>
                    <li><a href="test.jsp" class="nav-link active"><i class="bi bi-house navicon"></i>Home</a></li>
                    <li><a href="#services"><i class="bi bi-hdd-stack navicon"></i> Services</a></li>
                    <li><a href="#contact"><i class="bi bi-gear navicon"></i> Setting</a></li>
                    <li><a href="login.jsp"><i class="bi bi-box-arrow-right navicon"></i> Log out</a></li>
                    
                </ul>
            </nav>
        </header>

        <main id="main" class="main">
            <div id="content-container" class="content-wrapper"></div>
        </main>
    </div>

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Thêm jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <!-- Update JavaScript -->
    <script>
        (function() {
            "use strict";
            
            // Header toggle function
            const headerToggleBtn = document.querySelector('.header-toggle');
            function headerToggle() {
                document.querySelector('#header').classList.toggle('header-show');
                headerToggleBtn.classList.toggle('bi-list');
                headerToggleBtn.classList.toggle('bi-x');
            }
            headerToggleBtn.addEventListener('click', headerToggle);

            // Load content function
            function loadContent(url) {
                $.ajax({
                    url: url,
                    method: 'GET',
                    success: function(response) {
                        $('#content-container').html(response);
                    },
                    error: function() {
                        $('#content-container').html('<p>Error loading content</p>');
                    }
                });
            }

            // Navigation click handlers
            $('.nav-link').click(function(e) {
                e.preventDefault();
                
                // Remove active class from all links
                $('.nav-link').removeClass('active');
                // Add active class to clicked link
                $(this).addClass('active');
                
                // Load content
                const page = $(this).data('page');
                loadContent(page);
                
                // Close mobile menu if open
                if (document.querySelector('.header-show')) {
                    headerToggle();
                }
            });

            // Load home content by default
            loadContent('convert.jsp');
        })();
    </script>
</body>
</html>