<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create user</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>
<c:import url="header.jsp"/>
<div class="input-group">
    <form name="CreateUserForm" method="post" action="${pageContext.request.contextPath}/session">
        <input type="hidden" name="command" value="create_user"/>
        <h1>User creation</h1>
        <div>Username</div>
        <input type="text" name="login" placeholder="Username" required autofocus value="${login}"/>
        <div>First name</div>
        <input type="text" name="firstname" placeholder="First name" required value="${firstname}"/>
        <div>Second name</div>
        <input type="text" name="secondname" placeholder="Second name" required value="${secondname}"/>
        <div>Role</div>
        <select class="custom-select" id="role_id" name="role_id">
            <c:forEach items="${roles}" var="role">
                <option value="${role.id}" selected>${role.name}</option>
            </c:forEach>
        </select>
        <div>Password</div>
        <input type="password" name="password" placeholder="Password" required min="8" max="15" value=""/>
        <p><input type="submit" value="Create user"/></p>
        <p>${sessionScope.message}</p>
        <c:remove var="message" scope="session"/>
        <p>${errorMessage}</p>
    </form>
</div>

</body>
</html>
