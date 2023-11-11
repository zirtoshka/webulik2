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
    <title>points from zirtoshka special for kartoshka</title>
    <link rel="stylesheet" href="../assets/index.css">

</head>
<body>
<div class="result">
    <table id="results-table">
        <thead>
        <tr>
            <th >X</th>
            <th >Y</th>
            <th >R</th>
            <th >Current time</th>
            <th >Script time (ms)</th>
            <th >Result</th>
        </tr>
        </thead>
        <tbody id="results-content">
        <tr>
            <td>${x}</td>
            <td>${y}</td>
            <td>${r}</td>
            <td>${time}</td>
            <td>${script_time}</td>
            <td>${result}</td>
        </tr>
        </tbody>
    </table>
</div>
<div>
    <a href="/webulik2/index.jsp" class="back_to_form">go to form</a>
</div>

</body>
</html>
