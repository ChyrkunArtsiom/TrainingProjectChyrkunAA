<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<header>
    <c:import url="header.jsp"/>
</header>
<form name="CreateCourseForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_course"/>
    <p>Name: <input type="text" name="name" required max="45" value="${name}"/></p>
    <p>Teacher: <select id="teacher_id" name="teacher_id">
        <c:forEach items="${teachers}" var="teacher">
            <option value="${teacher.id}" selected>${teacher.firstname} ${teacher.secondname}</option>
        </c:forEach>
    </select>
    </p>
    <p>${errorMessage}</p>
    <p><input type="submit" value="Create course"/></p>
</form>
<footer>
    <c:import url="footer.jsp"/>
</footer>
</body>
</html>
