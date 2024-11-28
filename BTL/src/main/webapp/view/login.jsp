<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8"> 
  <title>Login Page</title>

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- Font Awesome -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

  <!-- Custom CSS -->
  <link href="../styles/login.css" rel="stylesheet">
</head>

<body>
  <div class="form-container">
    <div class="form-wrapper">
      <h2 class="text-center mb-4">Welcome Back</h2>
      
      <!-- Thêm id cho form và thêm action, method -->
      <form id="loginForm" action="Login" method="POST">
        <div class="form-group">
          <label for="email">Email address</label>
          <input type="email" 
                 class="form-control" 
                 id="email" 
                 name="email" 
                 required
                 placeholder="Enter email">
          <div id="emailError" class="text-danger"></div>
        </div>
        
        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" 
                 class="form-control" 
                 id="password" 
                 name="password" 
                 required
                 placeholder="Password">
          <div id="passwordError" class="text-danger"></div>
        </div>
        
        <button type="submit" class="btn btn-primary">Login</button>
      </form>
      
      <div class="text-center mt-3">
        <p class="text-muted">Don't have an account? <a href="signup.jsp" class="text-primary">Sign up</a></p>
      </div>
    </div>
  </div>
</body>
</html>
