<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create task</title>
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<header>
    <c:import url="header.jsp"/>
</header>
<form name="CreateTaskForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_task"/>
    <input type="hidden" name="course_id" value="${param.course_id}"/>
    <p>Name: <input type="text" name="name" required value="${name}"/></p>
    <p>Start date: <input type="date" name="startdate" required value="${startdate}"/></p>
    <p>Deadline: <input type="date" name="deadline" required value="${deadline}"/></p>
    <p>${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Create task"/></p>
</form>
<footer>
    <c:import url="footer.jsp"/>
</footer>
</body>
</html>
