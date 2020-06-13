<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 03.06.2020
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User profile</title>
</head>
<body>
<c:import url="header.jsp"/>
    <c:if test="${not empty user}">
        <p>User: ${user.id}</p>
        <p>Login: ${user.login}</p>
        <p>First name: ${user.firstname}</p>
        <p>Second name: ${user.secondname}</p>
        <p>Role: ${user.role.name}</p>
    </c:if>
    <p>${errorMessage}</p>
<c:import url="footer.jsp"/>
</body>
</html>
