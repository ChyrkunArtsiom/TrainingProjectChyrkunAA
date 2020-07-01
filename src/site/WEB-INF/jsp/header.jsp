<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">Training</a>
            </li>
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
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Log out</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>
</body>

</html>
