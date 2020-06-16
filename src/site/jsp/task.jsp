<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 16.06.2020
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task</title>
</head>
<body>
<c:import url="header.jsp"/>
<c:if test="${not empty task}">
    <p>Course: ${task.course.name}</p>
    <p>Name: ${task.name}</p>
    <p>Start date: ${task.startdate}</p>
    <p>Deadline: ${task.deadline}</p>
</c:if>
<p>${errorMessage}</p>
<c:import url="footer.jsp"/>
</body>
</html>
