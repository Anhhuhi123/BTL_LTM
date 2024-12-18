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