<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 20.05.2020
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Training</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>
<body>
<header>
    <c:import url="header.jsp"/>
</header>
<c:if test="${sessionScope.role eq 'admin'}">
    <c:import url="admin.jsp"/>
</c:if>
<c:if test="${sessionScope.role eq 'teacher'}">
    <c:import url="teacher.jsp"/>
</c:if>
<c:if test="${sessionScope.role eq 'student'}">
    <c:import url="student.jsp"/>
</c:if>
<footer>
</footer>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
