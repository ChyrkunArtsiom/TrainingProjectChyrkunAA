<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="courses"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>

<div class="container">
    <div class="col-md-8">
        <h1><fmt:message key="courses"/></h1>
        <c:if test="${not empty courses}">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="coursename"/></th>
                    <c:if test="${sessionScope.role eq 'student'}">
                        <th scope="col"><fmt:message key="teacher"/></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${courses}" var="course">
                    <tr class='clickable-row' data-href='${pageContext.request.contextPath}/course/${course.id}'>
                        <td>${course.name}</td>
                        <c:if test="${sessionScope.role eq 'student'}">
                            <td>${course.teacher.firstname} ${course.teacher.secondname}</td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <p>${sessionScope.message}</p>
        <c:remove var="message" scope="session"/>
        <p>${errorMessage}</p>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script>
    jQuery(document).ready(function($) {
        $(".clickable-row").click(function() {
            window.location = $(this).data("href");
        });
    });
</script>
</body>

</html>
