<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 08.06.2020
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create user form</title>
</head>
<body>
<form name="CreateUserForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_user"/>
    <p>Login: <input type="text" name="login" value="${login}"/></p>
    <p>Password: <input type="password" name="password" value=""/></p>
    <p>Password has to be from 8 to 15 characters with digits or underscore</p>
    <p>First name: <input type="text" name="firstname" value="${firstname}"/></p>
    <p>Second name: <input type="text" name="secondname" value="${secondname}"/></p>
    <c:if test="${sessionScope.role eq 'admin' && not empty roles}">
            <p>Role: <select id="role_id" name="role_id">
                <c:forEach items="${roles}" var="role">
                    <option value="${role.id}" selected>${role.name}</option>
                </c:forEach>
            </select>
            </p>
    </c:if>
    <p>${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Sign up"/></p>
    <a href="${pageContext.request.contextPath}/">On main page</a>
</form>
</body>
</html>
