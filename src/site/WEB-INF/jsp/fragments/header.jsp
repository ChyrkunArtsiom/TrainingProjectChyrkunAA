<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="ctg" uri="customtags" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title>Header</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<nav class="navbar navbar-expand navbar-dark bg-dark">
    <div class="collapse navbar-collapse">
        <ul class="navbar-nav mr-auto">
            <li>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/">Training</a>
            </li>
            <c:choose>
                <c:when test="${empty sessionScope.userName}">
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/signup"><fmt:message key="signup"/></a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/login"><fmt:message key="login"/></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/profile/${sessionScope.user_id}">${sessionScope.userName}</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout"><fmt:message key="logout"/></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    <div class="navbar-collapse collapse">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item active">
                <a class="nav-link" href="?sessionLocale=en_US">EN</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="?sessionLocale=ru_RU">RU</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="?sessionLocale=be_BY">BE</a>
            </li>
            <li class="nav-item active">
                <div class="nav-link">
                    <ctg:info-date/>
                </div>
            </li>
        </ul>
    </div>
</nav>

</body>

</html>
