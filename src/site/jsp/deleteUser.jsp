<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 03.06.2020
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete user</title>
</head>
<body>
<form name="DeleteUserForm" method="post" action="${pageContext.request.contextPath}/app">
    <input type="hidden" name="command" value="delete_user"/>
    <p>User id to delete: <input type="text" name="user_id" value=""/></p>
    <p>${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Delete"/></p>
</form>
</body>
</html>
