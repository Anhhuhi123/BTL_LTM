<%@ page import="java.util.List" %>
<%@ page import="model.bean.HistoryBean" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<html>
<head>
    <title>History Convert</title>
    <link href="../styles/history.css" rel="stylesheet">
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
