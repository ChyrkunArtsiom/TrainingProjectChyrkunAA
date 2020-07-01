<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css" >
</head>

<body>
<form name="CreateUserForm" class="form-signin" method="post" action="${pageContext.request.contextPath}/session">
    <div class="text-center">
        <h1 class="display-2"><a style="text-decoration: none" href="${pageContext.request.contextPath}/">Training</a></h1>
    </div>
    <input type="hidden" name="command" value="signup"/>
    <h1 class="h3 mb-3 font-weight-normal text-center">Sign up</h1>
    <div class="text-center">Username</div>
    <input type="text" name="login" class="form-control" placeholder="Username" required autofocus min="5" max="45" value="${login}"/>
    <div class="text-center">First name</div>
    <input type="text" name="firstname" class="form-control" placeholder="First name" required min="1" max="45" value="${firstname}"/>
    <div class="text-center">Second name</div>
    <input type="text" name="secondname" class="form-control" placeholder="Second name" required min="1" max="45" value="${secondname}"/>
    <div class="text-center">Password</div>
    <input type="password" name="password" class="form-control" placeholder="Password" required min="8" max="15" value=""/>
    <p><small>Make sure it's from 8 to 15 characters including numbers and underscore.</small></p>
    <input type="submit" class="btn btn-lg btn-primary btn-block" value="Sign up"/>
    <p class="text-center">${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p class="text-center">${errorMessage}</p>
</form>
</body>

</html>
