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
    <c:import url="fragments/header.jsp"/>
</header>

<c:choose>
    <c:when test="${not empty user}">
        <div class="container">
            <div class="row mt-3">
                <div class="col">
                    <c:if test="${not empty user}">
                        <p><fmt:message key="username"/>: ${user.login}</p>
                        <p><fmt:message key="firstname"/>: ${user.firstname}</p>
                        <p><fmt:message key="secondname"/>: ${user.secondname}</p>
                        <p><fmt:message key="role"/>: ${user.role.name}</p>
                    </c:if>
                </div>
            </div>
        </div>

        <form name="EditProfileForm" method="post" action="${pageContext.request.contextPath}/session">
            <input type="hidden" name="command" value="update_user"/>
            <input type="hidden" name="user_id" value="${user.id}"/>
            <input type="hidden" name="old_username" value="${user.login}"/>
            <div class="container">
                <div class="col-md-8">
                    <h1>Edit profile</h1>

                    <div class="mb-3">
                        <fmt:message key="username" var="lang_username"/>
                        <label for="inputlogin">${lang_username}</label>
                        <div class="input-group">
                            <input type="text" id="inputlogin" name="login" placeholder="${lang_username}" min="5" max="45" value="${user.login}"/>
                        </div>
                    </div>
                    <div class="mb-3">
                        <fmt:message key="firstname" var="lang_firstname"/>
                        <label for="inputfirstname">${lang_firstname}</label>
                        <div class="input-group">
                            <input type="text" id="inputfirstname" name="firstname" placeholder="${lang_firstname}" min="1" max="45" value="${user.firstname}"/>
                        </div>
                    </div>
                    <div class="mb-3">
                        <fmt:message key="secondname" var="lang_secondname"/>
                        <label for="inputsecondname">${lang_secondname}</label>
                        <div class="input-group">
                            <input type="text" id="inputsecondname" name="secondname" placeholder="${lang_secondname}" min="1" max="45" value="${user.secondname}"/>
                        </div>
                    </div>

                    <c:if test="${sessionScope.role eq 'admin'}">
                        <div class="row">
                            <div class="mb-3 col-md-3">
                                <label for="role_name"><fmt:message key="role"/></label>
                                <div class="input-group">
                                    <select class="custom-select" id="role_name" name="role_name">
                                        <c:forEach items="${roles}" var="role">
                                            <option value="${role.name}" selected>${role.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${sessionScope.role ne 'admin'}">
                        <div class="mb-3">
                            <fmt:message key="oldpassowrd" var="lang_oldpassword"/>
                            <label for="inputpassword">${lang_oldpassword}</label>
                            <div class="input-group">
                                <input type="password" id="input_oldpassword" name="old_password" placeholder="${lang_oldpassword}" required min="8" max="15" value=""/>
                            </div>
                        </div>
                        <div class="mb-3">
                            <fmt:message key="newpassword" var="lang_newpassword"/>
                            <fmt:message key="notRequired" var="lang_not_reguired"/>
                            <label for="inputpassword">${lang_newpassword}, ${lang_not_reguired}</label>
                            <div class="input-group">
                                <input type="password" id="inputpassword" name="new_password" placeholder="${lang_newpassword}" min="8" max="15" value=""/>
                            </div>
                        </div>
                    </c:if>

                    <input type="submit" class="btn btn-dark" value="Edit"/>
                    <p>${sessionScope.errorMessage}</p>
                    <c:remove var="message" scope="session"/>
                </div>
            </div>
        </form>
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="row col mt-3">
                <div class="col">
                    <p><fmt:message key="userNotFound"/></p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

</body>

</html>
