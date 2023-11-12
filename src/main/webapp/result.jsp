<%--
  Created by IntelliJ IDEA.
  User: zirtoshka
  Date: 12.11.2023
  Time: 01:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result Page</title>
</head>
<body>
<h2>Result Page</h2>
<table border="1">
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
    </tr>
    <tr>
        <td><%= request.getSession().getAttribute("x")%></td>
        <td><%= request.getAttribute("y")%></td>
        <td><%= request.getSession().getAttribute("r")%></td>
    </tr>
</table>
</body>
</html>
