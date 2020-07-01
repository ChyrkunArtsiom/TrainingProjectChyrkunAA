<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>User profile</title>
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
                <p>Login: ${user.login}</p>
                <p>First name: ${user.firstname}</p>
                <p>Second name: ${user.secondname}</p>
                <p>Role: ${user.role.name}</p>
            </c:if>
            <p>${errorMessage}</p>
        </div>
    </div>
</div>

</body>

</html>
