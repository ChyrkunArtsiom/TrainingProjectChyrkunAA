<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="header.login"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css" >
</head>

<body>
<form name="LogInForm" class="form-signin" method="post" action="${pageContext.request.contextPath}/session">
    <div class="text-center">
        <h1 class="display-2"><a style="text-decoration: none" href="${pageContext.request.contextPath}/">Training</a></h1>
    </div>
    <h1 class="h3 mb-3 font-weight-normal text-center"><fmt:message key="header.login"/></h1>
    <input type="hidden" name="command" value="login"/>
    <fmt:message key="username" var="lang_username"/>
    <input type="text" name="login" class="form-control" placeholder="${lang_username}" value="${login}" required autofocus/>
    <fmt:message key="password" var="lang_password"/>
    <input type="password" name="password" class="form-control" placeholder="${lang_password}" required/>
    <button type="submit" class="btn btn-lg btn-primary btn-block"><fmt:message key="login"/></button>
    <p class="text-center">${errorMessage}</p>
</form>
</body>

</html>
