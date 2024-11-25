<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<% String username = request.getParameter("username");
		String address = (String)request.getAttribute("address");
		String temp = (String)session.getAttribute("temp");
	%>
	Welcome = <%=username%>! <br>
	You are living at = <%=address %> <br>
	Temp = <%=temp %>;
</body>
</html>