
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String temp = "temp" ;
		session.setAttribute("temp",temp);
	%>
	<form name=forml action = "checkLogin.jsp" method = "post">
		Username: <input type = "text" name = "username" />
		Password: <input type = "text" name = "password" />
		
		<input type = "submit" value = "Login" />
		<input type = "reset" value = "Reset" />
	</form>
</body>
</html>