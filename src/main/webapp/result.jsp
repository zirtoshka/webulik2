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
    <link rel="stylesheet" href="assets/index.css">

    <title>Result Page</title>
</head>
<body>
<h2>Result Page</h2>
<% PointsStorage pointsStorage = (PointsStorage) request.getSession().getAttribute("tableContent");%>
<table id="results-table">
    <caption>Results</caption>
    <thead>
    <tr>
        <th width="5%">X</th>
        <th width="5%">Y</th>
        <th width="5%">R</th>
        <th width="40%">Current time</th>
        <th width="25%">Script time</th>
        <th width="20%">Result</th>
    </tr>
    </thead>
    <tbody id="results-content">
    <% List<Point> list = pointsStorage.getPoints(); %>
    <% for (int i = list.size() - 1; i >= 0; i--) { %>
    <tr>
        <td><%=list.get(i).getX()%>
        </td>
        <td><%=list.get(i).getY()%>
        </td>
        <td><%=list.get(i).getR()%>
        </td>
        <td><%= list.get(i).getTime() %>
        </td>
        <td><%= list.get(i).getScriptTime() %>
        </td>
        </td>
        <td><%= list.get(i).getIsKill() ? "kill"
                : "miss"%>
        </td>

    </tr>
    <% } %>
    </tbody>
</table>

<a href="/webulik2/index.jsp" class="back_to_form">go away form</a>
</body>
</html>
