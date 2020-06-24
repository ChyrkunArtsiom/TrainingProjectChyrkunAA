<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 13.06.2020
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Courses</title>
    <style>
        <%@include file="css/style.css" %>
    </style>
</head>
<body>
<header>
    <c:import url="header.jsp"/>
</header>
<c:if test="${not empty courses}">
    Courses:<br/>
    <table>
        <tr>
            <th>Name</th>
            <c:if test="${sessionScope.role eq 'student'}">
                <th>Teacher</th>
            </c:if>
        </tr>
        <c:forEach items="${courses}" var="course">
            <tr>
                <th><a href="${pageContext.request.contextPath}/course?command=course&course_id=${course.id}">${course.name}</a></th>
                <c:if test="${sessionScope.role eq 'student'}">
                    <th>${course.teacher.firstname} ${course.teacher.secondname}</th>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</c:if>
<p>${sessionScope.message}</p>
<c:remove var="message" scope="session"/>
${errorMessage}
<br/>
<footer>
    <c:import url="footer.jsp"/>
</footer>
</body>
</html>
