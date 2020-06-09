<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 09.06.2020
  Time: 13:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create course</title>
</head>
<body>
<form name="CreateCourseForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_course"/>
    <p>Name: <input type="text" name="name" value="${name}"/></p>
    <p>Teacher's id: <input type="text" name="teacher_id" value="${teacher_id}"/></p>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Create course"/></p>
    <a href="${pageContext.request.contextPath}/">On main page</a>
</form>
</body>
</html>
