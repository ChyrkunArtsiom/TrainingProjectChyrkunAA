<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Admin page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<div class="list-group">
    <a href="${pageContext.request.contextPath}/admin/createuser" class="list-group-item list-group-item-action">Create user</a>
    <a href="${pageContext.request.contextPath}/admin/deleteuser" class="list-group-item list-group-item-action">Delete user</a>
    <a href="${pageContext.request.contextPath}/admin/createcourse" class="list-group-item list-group-item-action">Create course</a>
</div>
<!--<p><a href="${pageContext.request.contextPath}/admin/deletecourse">Delete course</a> </p>
<p><a href="${pageContext.request.contextPath}/admin/updatecourse">Update course</a> </p>-->
</body>

</html>
