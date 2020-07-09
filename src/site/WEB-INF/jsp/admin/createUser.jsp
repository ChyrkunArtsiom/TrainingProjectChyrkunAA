<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="createUser"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="../fragments/header.jsp"/>
</header>
<fmt:message key="createUser" var="lang_createUser"/>
<form name="CreateUserForm" method="post" action="${pageContext.request.contextPath}/admin/createuser">
    <input type="hidden" name="command" value="create_user"/>
    <div class="container">
        <div class="col-md-8">
            <h1><fmt:message key="userCreation"/></h1>

            <div class="mb-3">
                <label for="inputlogin"><fmt:message key="username" var="lang_username"/></label>
                <div class="input-group">
                    <input type="text" id="inputlogin" name="username" placeholder="${lang_username}" required autofocus min="5" max="45" value="${username}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="inputfirstname"><fmt:message key="firstname" var="lang_firstname"/></label>
                <div class="input-group">
                    <input type="text" id="inputfirstname" name="firstname" placeholder="${lang_firstname}" required min="1" max="45" value="${firstname}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="inputsecondname"><fmt:message key="secondname" var="lang_secondname"/></label>
                <div class="input-group">
                    <input type="text" id="inputsecondname" name="secondname" placeholder="${lang_secondname}" required min="1" max="45" value="${secondname}"/>
                </div>
            </div>
            <div class="row">
                <div class="mb-3 col-md-3">
                    <label for="role_id"><fmt:message key="role"/></label>
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
                <label for="inputpassword"><fmt:message key="password" var="lang_password"/></label>
                <div class="input-group">
                    <input type="password" id="inputpassword" name="password" placeholder="${lang_password}" required min="8" max="15" value=""/>
                </div>
            </div>
            <input type="submit" class="btn btn-dark" value="<fmt:message key="createUser"/>"/>
            <p>${sessionScope.message}</p>
            <c:remove var="message" scope="session"/>
            <p>${errorMessage}</p>
        </div>
    </div>
</form>
</body>

</html>
