<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 11.06.2020
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete task</title>
</head>
<body>
<form name="DeleteTaskForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="delete_task"/>
    <p>Course id: <input type="text" name="task_id" value="${task_id}"/></p>
    <p>${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Delete task"/></p>
    <a href="${pageContext.request.contextPath}/">On main page</a>
</form>
</body>
</html>
