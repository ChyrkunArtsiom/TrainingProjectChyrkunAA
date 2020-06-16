<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create task</title>
</head>
<body>
<c:import url="header.jsp"/>
<form name="CreateTaskForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_task"/>
    <input type="hidden" name="course_id" value="${param.course_id}"/>
    <p>Name: <input type="text" name="name" value="${name}"/></p>
    <p>Start date: <input type="date" name="startdate" value="${startdate}"/></p>
    <p>Start date: <input type="date" name="deadline" value="${deadline}"/></p>
    <p>${sessionScope.message}</p>
    <c:remove var="message" scope="session"/>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Create task"/></p>
</form>
<c:import url="footer.jsp"/>
</body>
</html>
