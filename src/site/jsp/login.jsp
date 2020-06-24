<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<header></header>
<form name="LogInForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="login"/>
    <div class="login">
        <table>
            <tr>
                <td>Login:</td>
                <td><input type="text" name="login" required value="${login}"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" id="ps" name="password" required value=""/></td>
            </tr>
        </table>
    </div>
    <p>${errorMessage}</p>
    <input type="submit" class="login_button" value="Log in"/>
</form>
<footer>
    <c:import url="footer.jsp"/>
</footer>
</body>
</html>
