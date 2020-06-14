<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 09.06.2020
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Teacher page</title>
</head>
<body>
<h3>Teacher page</h3>
<c:import url="header.jsp"/>
<p><a href="${pageContext.request.contextPath}/teacher/courses">Show courses</a> </p>
<br/>
<p><a href="${pageContext.request.contextPath}/teacher/createtask">Create task</a> </p>
<p><a href="${pageContext.request.contextPath}/teacher/deletetask">Delete task</a> </p>
<p><a href="${pageContext.request.contextPath}/teacher/opentask">Open task</a> </p>
<c:import url="footer.jsp"/>
</body>
</html>
