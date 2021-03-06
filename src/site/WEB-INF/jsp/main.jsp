<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title>Training</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="fragments/header.jsp"/>
</header>
<c:if test="${sessionScope.role eq 'admin'}">
    <c:import url="admin/admin.jsp"/>
</c:if>
<c:if test="${sessionScope.role eq 'teacher'}">
    <c:import url="teacher/teacher.jsp"/>
</c:if>
<c:if test="${sessionScope.role eq 'student'}">
    <c:import url="student/student.jsp"/>
</c:if>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>

</html>
