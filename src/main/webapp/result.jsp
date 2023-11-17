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
    <style>
        #results-table {
            width: 100%
        }
        a{
            font-size: larger;
        }

        a:hover { color: green; }
        a:active { color: #8c00ff; }
    </style>
</head>
<body>
<a href="/webulik2/index.jsp" class="back_to_form">go away form</a>
<% PointsStorage pointsStorage = (PointsStorage) request.getSession().getAttribute("tableContent");%>
<br>
<br>
<table id="results-table">
    <thead>
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Current time</th>
        <th>Script time</th>
        <th>Result</th>
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


</body>
</html>
