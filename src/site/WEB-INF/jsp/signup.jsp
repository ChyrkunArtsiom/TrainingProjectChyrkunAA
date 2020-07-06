<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="signup"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css" >
</head>

<body>
<form name="CreateUserForm" class="form-signin" method="post" action="${pageContext.request.contextPath}/session">
    <div class="text-center">
        <h1 class="display-2"><a style="text-decoration: none" href="${pageContext.request.contextPath}/">Training</a></h1>
    </div>
    <input type="hidden" name="command" value="signup"/>
    <h1 class="h3 mb-3 font-weight-normal text-center"><fmt:message key="signup"/></h1>
    <div class="text-center"><fmt:message key="username" var="lang_username"/></div>
    <input type="text" name="login" class="form-control" placeholder="${lang_username}" required autofocus min="5" max="45" value="${login}"/>
    <div class="text-center"><fmt:message key="firstname" var="lang_firstname"/></div>
    <input type="text" name="firstname" class="form-control" placeholder="${lang_firstname}" required min="1" max="45" value="${firstname}"/>
    <div class="text-center"><fmt:message key="secondname" var="lang_secondname"/></div>
    <input type="text" name="secondname" class="form-control" placeholder="${lang_secondname}" required min="1" max="45" value="${secondname}"/>
    <div class="text-center"><fmt:message key="password" var="lang_password"/></div>
    <input type="password" name="password" class="form-control" placeholder="${lang_password}" required min="8" max="15" value=""/>
    <p><small><fmt:message key="password_clarification"/></small></p>
    <button type="submit" class="btn btn-lg btn-primary btn-block"><fmt:message key="signup"/></button>
    <p class="text-center">${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p class="text-center">${errorMessage}</p>
</form>
</body>

</html>
