<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 03.06.2020
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<form name="LogInForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="login"/>
    <p>Login: <input type="text" name="login" value="${login}"/></p>
    <p>Password: <input type="password" name="password" value=""/></p>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Log in"/></p>
</form>
</body>
</html>
