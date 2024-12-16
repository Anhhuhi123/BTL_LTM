<%@ page import="java.util.List" %>
<%@ page import="model.bean.HistoryBean" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <title>History Convert</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f9;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-top: 20px;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            font-size: 14px;
            color: white;
            background-color: #007bff;
            text-decoration: none;
            border-radius: 4px;
            margin: 10px 0;
            transition: background-color 0.3s ease;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .btn-container {
            text-align: right;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <h1>History Convert</h1>
    <div class="container">
       <%
       List<HistoryBean> listHistory = (List<HistoryBean>) request.getSession().getAttribute("listHistory");
       %>

        <table>
            <tr>
                <th>File Input</th>
                <th>File Output</th>
                <th>Date</th>
            </tr>

            <%
            if (listHistory != null && !listHistory.isEmpty()) {
                for (HistoryBean history : listHistory) {
            %>
                <tr>
                    <td><%= history.getFilePdf() %></td>
                    <td>
                    	<a
	                	href="./download?action=downloadfile&fileName=<%=history.getFileDocx()%>"
	                	target="_blank"
	              		>
	              		<%=history.getFileDocx().substring(history.getFileDocx().indexOf('_') + 1)%>
	              		</a>
                    </td>
                    <td><%= history.getDate() %></td>
                </tr>
            <%
                }
            } else {
            %>
                <tr>
                    <td colspan="3" style="text-align: center; color: #888;">Không có dữ liệu nào</td>
                </tr>
            <%
            }
            %>
        </table>
        <button type="button" class="btn-back" onclick="history.back()">Quay lại</button>
    </div>
</body>
</html>
