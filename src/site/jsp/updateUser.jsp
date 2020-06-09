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
    <title>Title</title>
</head>
<body>
<form name="UpdateForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="update_user"/>
    <p>Login: <input type="text" name="login" value="${login}"/></p>
    <p>Password: <input type="password" name="password" value="${password}"/></p>
    <p>Password has to be from 8 to 15 characters with digits or underscore</p>
    <p>First name: <input type="text" name="firstname" value="${firstname}"/></p>
    <p>Second name: <input type="text" name="secondname" value="${secondname}"/></p>
    <c:if test="${sessionScope.role eq 'admin'}">
        <p>Role id: <input type="text" name="role_id" value=""/></p>
    </c:if>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Update user"/></p>
</form>
</body>
</html>
