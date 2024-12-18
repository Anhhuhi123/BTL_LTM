<%@ page import="java.util.List" %>
<%@ page import="model.bean.HistoryBean" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>History Convert</title>
    <link href="../styles/history.css" rel="stylesheet">
</head>
<body>
    <%
        if (request.getSession(false) == null || request.getSession().getAttribute("email") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <h1>History Convert</h1>
    <div class="container">
        <%
            List<HistoryBean> listHistory = (List<HistoryBean>) request.getSession().getAttribute("listHistory");
        %>

        <table>
            <tr>
                <th>No</th>
                <th>File Input</th>
                <th>File Output</th>
                <th>Date</th>
            </tr>

            <%
            if (listHistory != null && !listHistory.isEmpty()) {
                int stt = 1; // Biến đếm STT bắt đầu từ 1
                for (HistoryBean history : listHistory) {
            %>
                <tr>
                    <td><%= stt++ %></td> <!-- Hiển thị số thứ tự và tăng dần -->
                    <td><%= history.getFilePdf() %></td>
                    <td>
                        <a href="./download?action=downloadfile&fileName=<%=history.getFileDocx()%>"
                           target="_blank">
                            <%= history.getFileDocx().substring(history.getFileDocx().indexOf('_') + 1)%>
                        </a>
                    </td>
                    <td><%= history.getDate() %></td>
                </tr>
            <%
                }
            } else {
            %>
                <tr>
                    <td colspan="4" style="text-align: center; color: #888;">Không có dữ liệu nào</td>
                </tr>
            <%
            }
            %>
        </table>
  <!--       <div class="btn-container-center">
            <button type="button" class="btn btn-back" onclick="history.back()">Quay lại</button>
        </div> -->
    </div>
</body>
</html>
