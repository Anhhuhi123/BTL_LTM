<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">	
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <!-- Fonts -->
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&family=Poppins:wght@300;400;500;600&family=Raleway:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <!-- Bootstrap CSS & Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="../styles/main.css" rel="stylesheet">  
</head>
<body>
	<%
	    if (request.getSession(false) == null || request.getSession().getAttribute("email") == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }
	%>	
    <div class="wrapper">
        <header id="header" class="header">
            <i class="header-toggle d-xl-none bi bi-list"></i>

            <div class="profile-img">
                <img src="../styles/img/convertimg.png" alt="" class="img-fluid rounded-circle">
            </div>

            <a href="main.jsp" class="logo d-flex align-items-center justify-content-center">
                <h1 class="sitename">Convert</h1>
            </a>
	
            <div class="social-links text-center">
                
                <a  class="twitter"><i class="bi bi-twitter-x"></i></a>
                <a  class="facebook"><i class="bi bi-facebook"></i></a>
                <a  class="instagram"><i class="bi bi-instagram"></i></a>
                <a  class="google-plus"><i class="bi bi-skype"></i></a>
                <a  class="linkedin"><i class="bi bi-linkedin"></i></a>
            </div>

            <!-- <nav id="navmenu" class="navmenu">
                <ul>
                    <li><a href="test.jsp" class="nav-link active"><i class="bi bi-house navicon"></i>Home</a></li>
                    <li><a href="./historyConvert"><i class="bi bi-hdd-stack navicon"></i> History</a></li>
                    <li><a href="./logout"><i class="bi bi-box-arrow-right navicon"></i> Log out</a></li>
                     
                </ul>
            </nav> -->
            
            <nav id="navmenu" class="navmenu">
			    <ul>
			        <li><a href="./main.jsp"><i class="bi bi-house navicon"></i>Home</a></li>
			        <li><a href="./historyConvert" class="nav-link" data-page="historyConvert.jsp"><i class="bi bi-hdd-stack navicon"></i> History</a></li>
			        <li><a href="./logout"><i class="bi bi-box-arrow-right navicon"></i> Log out</a></li>
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
    <script src="../JS/main.js"></script>
</body>
</html>