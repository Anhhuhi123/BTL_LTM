<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Sign Up Page</title>

  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- Font Awesome -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">

  <!-- jQuery -->
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	  <!-- Custom CSS --> 

  <link href="../styles/signup.css" rel="stylesheet">

</head>

<body>
  <div class="form-container">
    <div class="form-wrapper">
      <h2 class="text-center mb-4">Create Account</h2>
      <% if (request.getAttribute("error") != null) { %>
	    <span style="color: red;text-align: center; margin:0px; display: block;">
	      <%= request.getAttribute("error") %>
	    </span>
	  <% } %>
      <form id="signupForm" method="POST" action="Register"> 
        <div class="form-group">
          <label for="fullName">Full Name</label>
          <input type="text" class="form-control" id="fullName" name="fullName" placeholder="Enter your full name">
          <span class="form-message"></span>
        </div>

        <div class="form-group">
          <label for="email">Email address</label>
          <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
          <span class="form-message"></span>
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" id="password"  name="password" placeholder="Create password">
          <span class="form-message"></span>
        </div>

        <div class="form-group">
          <label for="confirmPassword">Confirm Password</label>
          <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm password">
          <span class="form-message"></span>
        </div>

        <button type="submit" class="btn btn-success btn-submit">Create Account</button>
      </form>
      
      <div class="text-center mt-3">
        <p class="text-muted">Already have an account? <a href="./Login" class="text-success">Login here</a></p>
      </div>
    </div>
  </div>

  <script src="../JS/signup.js"></script>
</body>
</html> 