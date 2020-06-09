<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 26.05.2020
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Roles</title>
</head>
<body>
    <c:forEach items="${roles}" var="role">
        <p><input type="radio" name="role" value="${role.name}"/>${role.name}</p>
    </c:forEach>
    <p>${errorRolesMessage}</p>
    <form method="get" action="${pageContext.request.contextPath}/app">
        <input type="hidden" name="command" value="get_roles"/>
        <p><input type="submit" value="Sign up"/></p>
    </form>
</body>
</html>
