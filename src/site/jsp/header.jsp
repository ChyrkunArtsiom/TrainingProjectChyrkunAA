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
</head>
<body>
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
            <br/><input type="submit" value="Log out"/>
        </form>
    </c:otherwise>
</c:choose>
<br/>
-----------------------------------------------------------------
<br/>
</body>
</html>