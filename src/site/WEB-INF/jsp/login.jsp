<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Log in</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signin.css" >
</head>

<body>
<form name="LogInForm" class="form-signin" method="post" action="${pageContext.request.contextPath}/session">
    <div class="text-center">
        <h1 class="display-2"><a style="text-decoration: none" href="${pageContext.request.contextPath}/">Training</a></h1>
    </div>
    <h1 class="h3 mb-3 font-weight-normal text-center">Sign in</h1>
    <input type="hidden" name="command" value="login"/>
    <input type="text" name="login" class="form-control" placeholder="Login"  value="${login}" required autofocus/>
    <input type="password" id="ps" name="password" class="form-control" placeholder="Password" required/>
    <input type="submit" class="btn btn-lg btn-primary btn-block" value="Log in"/>
    <p class="text-center">${errorMessage}</p>
</form>
</body>

</html>
