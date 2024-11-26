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
  <link href="auth-style.css" rel="stylesheet">
</head>

<body>
  <div class="form-container">
    <div class="form-wrapper">
      <h2 class="text-center mb-4">Create Account</h2>
      <form>
<!--         <div class="avatar-upload">
          <div class="avatar-preview">
            <img src="https://via.placeholder.com/150" alt="Avatar preview" id="avatarPreview">
          </div>
          <div class="form-group">
            <label for="avatarUpload" class="form-label">Profile Picture</label>
            <input type="file" class="form-control" id="avatarUpload" accept="image/*">
          </div>
        </div> -->

        <div class="form-group">
          <label for="fullName">Full Name</label>
          <input type="text" class="form-control" id="fullName" placeholder="Enter your full name">
        </div>

        <div class="form-group">
          <label for="email">Email address</label>
          <input type="email" class="form-control" id="email" placeholder="Enter email">
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" class="form-control" id="password" placeholder="Create password">
          <div class="password-requirements">
            Password must contain at least 8 characters, including uppercase, lowercase letters and numbers
          </div>
        </div>

        <div class="form-group">
          <label for="confirmPassword">Confirm Password</label>
          <input type="password" class="form-control" id="confirmPassword" placeholder="Confirm password">
        </div>

        <div class="form-group">
          <label for="phoneNumber">Phone Number</label>
          <input type="tel" class="form-control" id="phoneNumber" placeholder="Enter phone number">
        </div>

        <div class="form-group form-check">
          <input type="checkbox" class="form-check-input" id="termsCheck">
          <label class="form-check-label" for="termsCheck">I agree to the Terms and Conditions</label>
        </div>

        <button type="submit" class="btn btn-success">Create Account</button>
      </form>
      
      <div class="text-center mt-3">
        <p class="text-muted">Already have an account? <a href="login.jsp" class="text-success">Login here</a></p>
      </div>
    </div>
  </div>

  <script>
    // Preview uploaded avatar image
    document.getElementById('avatarUpload').addEventListener('change', function(e) {
      const file = e.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
          document.getElementById('avatarPreview').src = e.target.result;
        }
        reader.readAsDataURL(file);
      }
    });
  </script>
</body>
</html> 