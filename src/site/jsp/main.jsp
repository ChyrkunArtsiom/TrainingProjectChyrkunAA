<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 20.05.2020
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<p>Training</p>
<br/>
<c:choose>
    <c:when test="${empty sessionScope.userName}">
        <p><a href="${pageContext.request.contextPath}/signup">Sign up</a></p>
        <p><a href="${pageContext.request.contextPath}/login">Log in</a></p>
    </c:when>
    <c:otherwise>
        <p>Signed in as
            <a href="${pageContext.request.contextPath}/userProfile?command=profile&login=${sessionScope.userName}">${sessionScope.userName}</a>
        </p>
        </form>
        <form name="LogoutForm" method="post" action="${pageContext.request.contextPath}/app">
            <input type="hidden" name="command" value="logout"/>
            <p><input type="submit" value="Log out"/></p>
        </form>
        <c:if test="${sessionScope.role eq 'admin'}">
            <p><a href="${pageContext.request.contextPath}/admin">Admin page</a> </p>
        </c:if>
        <c:if test="${sessionScope.role eq 'teacher'}">
            <p><a href="${pageContext.request.contextPath}/teacher">Teacher page</a> </p>
        </c:if>
    </c:otherwise>
</c:choose>
<a href="${pageContext.request.contextPath}/roles">roles</a>
</body>
</html>
