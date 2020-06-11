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
</head>
<body>
<c:import url="header.jsp"/>
<c:if test="${sessionScope.role eq 'admin'}">
    <p><a href="${pageContext.request.contextPath}/admin">Admin page</a> </p>
</c:if>
<c:if test="${sessionScope.role eq 'teacher'}">
    <p><a href="${pageContext.request.contextPath}/teacher">Teacher page</a> </p>
</c:if>
<a href="${pageContext.request.contextPath}/roles">roles</a>
</body>
</html>
