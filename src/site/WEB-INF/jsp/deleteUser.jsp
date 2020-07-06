<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="deleteUser"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>
<form name="DeleteUserForm" method="post" action="${pageContext.request.contextPath}/admin/deleteuser">
    <input type="hidden" name="command" value="delete_user"/>
    <div class="container">
        <div class="col-md-8">
            <h1><fmt:message key="userDeletion"/></h1>

            <div class="mb-3">
                <label for="inputlogin"><fmt:message key="userToDelete"/></label>
                <div class="input-group">
                    <input id="inputlogin" class="form-control" type="text" name="login" required value="${login}"/>
                </div>
            </div>
            <button type="submit" class="btn btn-dark"><fmt:message key="deleteUser"/></button>
            <p>${sessionScope.message}</p>
            <c:remove var="message" scope="session"/>
            <p>${errorMessage}</p>
        </div>
    </div>
</form>
</body>

</html>
