<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title>Course<fmt:message key="course"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>

<body>
<header>
    <c:import url="fragments/header.jsp"/>
</header>

<c:choose>
    <c:when test="${not empty course}">
        <div class="container">
            <div class="row col mt-3">
                <div class="col">
                    <p><fmt:message key="coursename"/>: ${course.name}</p>
                    <p><fmt:message key="teacher"/>: ${course.teacher.firstname} ${course.teacher.secondname}</p>
                    <c:if test="${sessionScope.role eq 'admin'}">
                        <form name="DeleteCourseForm" method="post" action="${pageContext.request.contextPath}/session">
                            <input type="hidden" name="command" value="delete_course"/>
                            <input type="hidden" name="course_id" value="${course.id}"/>
                            <fmt:message key="deleteCourse" var="lang_deleteCourse"/>
                            <input type="submit" class="btn btn-dark" value="${lang_deleteCourse}"/>
                        </form>
                    </c:if>
                    <c:if test="${sessionScope.role eq 'student' and registered eq 'false'}">
                        <form name="RegisterForm" method="post" action="${pageContext.request.contextPath}/session">
                            <input type="hidden" name="command" value="register_course"/>
                            <input type="hidden" name="course_id" value="${course.id}"/>
                            <fmt:message key="registerCourse" var="lang_registerCourse"/>
                            <input type="submit" class="btn btn-dark" value="${lang_registerCourse}"/>
                        </form>
                    </c:if>
                    <p>${errorMessage}</p>

                    <c:if test="${not empty tasks and (sessionScope.role eq 'teacher' or (sessionScope.role eq 'student' and registered ne 'false'))}">
                        <div class="container">
                            <div class="col-md-8">
                                <h1><fmt:message key="tasks"/></h1>
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="taskName"/></th>
                                        <th><fmt:message key="startdate"/></th>
                                        <th><fmt:message key="deadline"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${tasks}" var="task">
                                        <tr class='clickable-row' data-href='${pageContext.request.contextPath}/task/${task.id}'>
                                            <td>${task.name}</td>
                                            <td>${task.startdate}</td>
                                            <td>${task.deadline}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="row col mt-3">
                <div class="col">
                    <p><fmt:message key="courseNotFound"/></p>
                </div>
            </div>
        </div>
    </c:otherwise>
</c:choose>

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