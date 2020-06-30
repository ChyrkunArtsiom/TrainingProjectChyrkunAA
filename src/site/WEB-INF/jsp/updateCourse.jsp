<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 09.06.2020
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update course</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>
<header>
    <c:import url="header.jsp"/>
</header>
<form name="UpdateCourseForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="update_course"/>
    <p>Course id: <input type="text" name="course_id" value="${course_id}"/></p>
    <p>Name: <input type="text" name="course_name" value="${course_name}"/></p>
    <p>Teacher id: <input type="text" name="teacher_id" value="${teacher_id}"/></p>
    <p>${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Update course"/></p>
    <a href="${pageContext.request.contextPath}/">On main page</a>
</form>
<footer>
    <c:import url="footer.jsp"/>
</footer>
</body>
</html>
