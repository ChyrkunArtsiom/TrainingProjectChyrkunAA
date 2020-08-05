<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="adminpage"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<div class="list-group">
    <a href="${pageContext.request.contextPath}/admin/createuser" class="list-group-item list-group-item-action"><fmt:message key="createUser"/></a>
    <a href="${pageContext.request.contextPath}/admin/createcourse" class="list-group-item list-group-item-action"><fmt:message key="createCourse"/></a>
    <a href="${pageContext.request.contextPath}/admin/courses/1" class="list-group-item list-group-item-action"><fmt:message key="courses"/></a>
</div>
<!--<p><a href="${pageContext.request.contextPath}/admin/deletecourse">Delete course</a> </p>
<p><a href="${pageContext.request.contextPath}/admin/updatecourse">Update course</a> </p>-->
</body>

</html>
