<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Student page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<div class="list-group">
    <a href="${pageContext.request.contextPath}/student/courses" class="list-group-item list-group-item-action">Courses</a>
    <a href="${pageContext.request.contextPath}/student/registered" class="list-group-item list-group-item-action">Registered courses</a>
</div>
<div class="container">
    <div class="row mt-1">
        <p>${sessionScope.message}</p>
        <c:remove var="message" scope="session"/>
    </div>
</div>
</body>

</html>
