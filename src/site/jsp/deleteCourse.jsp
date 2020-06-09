<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 09.06.2020
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete course</title>
</head>
<body>
<form name="DeleteCourseForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="delete_course"/>
    <p>Course id: <input type="text" name="course_id" value="${course_id}"/></p>
    <p>${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Delete course"/></p>
    <a href="${pageContext.request.contextPath}/">On main page</a>
</form>
</body>
</html>
