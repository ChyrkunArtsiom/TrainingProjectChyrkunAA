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
</head>
<body>
<c:import url="header.jsp"/>
<c:forEach items="${courses}" var="course">
    <p><a href="${pageContext.request.contextPath}/course?command=course&course_id=${course.id}">${course.name}</a></p>
</c:forEach>
<p>${sessionScope.message}</p>
<c:remove var="message" scope="session"/>
${errorMessage}
<c:import url="footer.jsp"/>
</body>
</html>
