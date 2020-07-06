<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="userProfile"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>
<div class="container">
    <div class="row mt-3">
        <div class="col">
            <c:if test="${not empty user}">
                <p><fmt:message key="username"/>: ${user.login}</p>
                <p><fmt:message key="firstname"/>: ${user.firstname}</p>
                <p><fmt:message key="secondname"/>: ${user.secondname}</p>
                <p><fmt:message key="role"/>: ${user.role.name}</p>
            </c:if>
            <p>${errorMessage}</p>
        </div>
    </div>
</div>

</body>

</html>
