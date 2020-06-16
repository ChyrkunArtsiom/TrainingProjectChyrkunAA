<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 07.06.2020
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<p><a href="${pageContext.request.contextPath}/admin/createuser">Create user</a> </p>
<p><a href="${pageContext.request.contextPath}/admin/deleteuser">Delete user</a> </p>
<br/>
<p><a href="${pageContext.request.contextPath}/admin/createcourse">Create course</a> </p>
<p><a href="${pageContext.request.contextPath}/admin/deletecourse">Delete course</a> </p>
<p><a href="${pageContext.request.contextPath}/admin/updatecourse">Update course</a> </p>
</body>
</html>
