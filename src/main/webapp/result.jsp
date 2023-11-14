<%@ page import="validator.PointsStorage" %>
<%@ page import="validator.Point" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="java.util.stream.Collectors" %><%--
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
<% PointsStorage pointsStorage = (PointsStorage) request.getSession().getAttribute("tableContent");%>
<table border="1">
    <caption>Все результаты</caption>
    <thead>
    <th>x</th>
    <th>y</th>
    <th>R</th>
    <th>Результат</th>
    <th>Script time</th>
    <th>Текущее время</th>
    </thead>
    <tbody>
    <% List<Point> list = pointsStorage.getPoints(); %>
<%--    <% list = list.stream().sorted(Comparator.comparing(Point::getTime).reversed()).collect(Collectors.toList()); %>--%>
    <% for (Point point : list) { %>
    <tr>
        <td><%=point.getX()%>
        </td>
        <td><%=point.getY()%>
        </td>
        <td><%=point.getR()%>
        </td>
        <td><%= point.getIsKill() ? "kill"
                : "miss"%>
        </td>
        <td><%= point.getScriptTime() %>
        </td>
        </td>
        <td><%= point.getTime() %>
        </td>

    </tr>
    <% } %>
    </tbody>
</table>

<a href="/webulik2/index.jsp" class="back_to_form">go away form</a>
</body>
</html>
