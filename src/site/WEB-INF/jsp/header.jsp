<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 11.06.2020
  Time: 20:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>
<nav class="navbar navbar-expand navbar-dark bg-dark">
    <div class="collapse navbar-collapse" id="navbarsExample02">
        <ul class="navbar-nav mr-auto">
            <c:choose>
                <c:when test="${empty sessionScope.userName}">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/signup">Sign up</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login">Log in</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/userProfile?command=profile&login=${sessionScope.userName}">${sessionScope.userName}</a>
                    </li>
                    <li class="nav-item active">
                        <form name="LogoutForm" method="post" action="${pageContext.request.contextPath}/app">
                            <input type="hidden" name="command" value="logout"/>
                            <input type="submit" value="Log out"/>
                        </form>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
</body>
</html>
