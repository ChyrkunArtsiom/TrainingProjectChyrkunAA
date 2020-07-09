<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="studentpage"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<div class="list-group">
    <a href="${pageContext.request.contextPath}/student/courses" class="list-group-item list-group-item-action"><fmt:message key="courses"/></a>
    <a href="${pageContext.request.contextPath}/student/registered" class="list-group-item list-group-item-action"><fmt:message key="registeredCourses"/></a>
</div>
<div class="container">
    <div class="row mt-1">
        <p>${sessionScope.message}</p>
        <c:remove var="message" scope="session"/>
    </div>
</div>
</body>

</html>
