<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Create user</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>
<form name="CreateUserForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_user"/>
    <div class="container">
        <div class="col-md-8">
            <h1>User creation</h1>

            <div class="mb-3">
                <label for="inputlogin">Username</label>
                <div class="input-group">
                    <input type="text" id="inputlogin" name="login" placeholder="Username" required autofocus min="5" max="45" value="${login}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="inputfirstname">First name</label>
                <div class="input-group">
                    <input type="text" id="inputfirstname" name="firstname" placeholder="First name" required min="1" max="45" value="${firstname}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="inputsecondname">Second name</label>
                <div class="input-group">
                    <input type="text" id="inputsecondname" name="secondname" placeholder="Second name" required min="1" max="45" value="${secondname}"/>
                </div>
            </div>
            <div class="row">
                <div class="mb-3 col-md-3">
                    <label for="role_id">Role</label>
                    <div class="input-group">
                        <select class="custom-select" id="role_id" name="role_id">
                            <c:forEach items="${roles}" var="role">
                                <option value="${role.id}" selected>${role.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="mb-3">
                <label for="inputpassword">Password</label>
                <div class="input-group">
                    <input type="password" id="inputpassword" name="password" placeholder="Password" required min="8" max="15" value=""/>
                </div>
            </div>
            <input type="submit" class="btn btn-dark" value="Create user"/>
            <p>${sessionScope.message}</p>
            <c:remove var="message" scope="session"/>
            <p>${errorMessage}</p>
        </div>
    </div>
</form>
</body>

</html>
