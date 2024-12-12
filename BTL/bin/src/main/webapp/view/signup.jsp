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

  <script>
  		const handleFullName = (value)=>{
  			const trimValue = value.trim();
  			if(trimValue.length === 0){
  				return "Require";
  			}
  			if(value !== trimValue){
  				return "FullName cannot have leading or trailing spaces";
  			}
  			if(trimValue.length < 5){
  				return "FullName must be at least 5 characters"
  			}
  			return false;
  			
  		}
  		const handleEmail = (value) => {
  		  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  		  if (emailRegex.test(value.trim())) {
  			  	return false;
  		  } 
  		  if(!value.trim()){
  				return "Require";
  		  }
  		  else {
  		    	return "Invalid email";
  		  }
  		};

  		const handlePassword = (value) => {
  		  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{5,}$/;
  		  if (passwordRegex.test(value.trim())) {
  		    	return false;
  		  }
  		  if(!value.trim()){
				return "Require";
		  }
  		  else {
  		    	return "Must be at least 5 characters, one letter, one number and one special character."
  		  }
  		};
  		
  		const handleConfirmPassword = (value) => {
  		  const password = document.getElementById("password").value;

  		  if(!value.trim()){
  			  return "Require";
  		  }
  		  else if (value.trim() === password.trim()) {
  		    return false;
  		  }
  		  else {
  		    return "Password does not match";
  		  }
  		};

    	const rules = [
    		{
    			title: "fullName",
    			test: handleFullName,
    		},
    		 {
    			title: "email",
    			test: handleEmail,
    		},
    		{
    			title: "password",
    			test: handlePassword,
    		},
    		{
    			title: "confirmPassword",
    			test: handleConfirmPassword,
    		} 
    	];
    	rules.forEach((rule,index)=>{
    		const inputElement = document.getElementById(rule.title);
    		let errorMessage;
    		inputElement.onblur = (e)=>{
    			errorMessage = rule.test(inputElement.value);
    			if (errorMessage) {
    				  inputElement.parentElement.querySelector(".form-message").textContent = errorMessage;
    			}
    		}
    		inputElement.oninput = (e)=>{
    			inputElement.parentElement.querySelector(".form-message").textContent = "";
    		}
    	})
    	
    	
    	 const formElement = document.getElementById("signupForm");
    		  formElement.onsubmit = (e) => {
		      let hasError = false;
		      rules.forEach(rule => {
		        const inputElement = document.getElementById(rule.title);
		        const errorMessage = rule.test(inputElement.value);
		        const messageElement = inputElement.parentElement.querySelector(".form-message");
		        if (errorMessage) {
		          messageElement.textContent = errorMessage;
		          hasError = true;
		        }
		      });
		      if (hasError) {
		        e.preventDefault(); 
		      }
		 };
    	
  </script>
</body>
</html> 