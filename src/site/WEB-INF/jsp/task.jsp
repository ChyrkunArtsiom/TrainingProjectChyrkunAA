<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="task"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="fragments/header.jsp"/>
</header>
<c:choose>
    <c:when test="${not empty task}">
        <div class="container">
            <div class="row col mt-3">
                <div class="col">
                    <p><fmt:message key="coursename"/>: ${task.course.name}</p>
                    <p><fmt:message key="taskName"/>: ${task.name}</p>
                    <p><fmt:message key="startdate"/>: ${task.startdate}</p>
                    <p><fmt:message key="deadline"/>: ${task.deadline}</p>

                    <fmt:parseDate value="${task.deadline}" pattern="yyyy-MM-dd" var="pattern"/>
                    <fmt:formatDate var="deadline" value="${pattern}" pattern="dd/MMM/yyyy"/>
                    <jsp:useBean id="now" class="java.util.Date"/>
                    <fmt:formatDate var="today" value="${now}" pattern="dd/MMM/yyyy"/>

                    <c:if test="${sessionScope.role eq 'teacher'}">
                        <form name="DeleteTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                            <input type="hidden" name="command" value="delete_task"/>
                            <input type="hidden" name="task_id" value="${task.id}"/>
                            <input type="hidden" name="course_id" value="${task.course.id}"/>
                            <input type="submit" class="btn btn-dark" value="<fmt:message key="deleteTask"/>"/>
                        </form>
                        <div class="container">
                            <div class="col-md-8">
                                <h1><fmt:message key="completedTasks"/></h1>
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th><fmt:message key="student"/></th>
                                        <th><fmt:message key="grade"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${registrations}" var="registration">
                                        <tr class='clickable-row' data-href='${pageContext.request.contextPath}/exercise/${registration.id}'>
                                            <td>${registration.student.firstname} ${registration.student.secondname}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${registration.grade ne '0'}">${registration.grade}</c:when>
                                                    <c:otherwise><fmt:message key="notReviewed"/></c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.role eq 'student'}">
                        <c:choose>
                            <c:when test="${performed eq 'true'}">
                                <c:choose>
                                    <c:when test="${reviewed eq 'true'}">
                                        <p><fmt:message key="grade"/>: ${exercise.grade}</p>
                                        <p><fmt:message key="review"/>: ${exercise.review}</p>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${deadline ge today}">
                                            <form name="CancelTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                                                <input type="hidden" name="command" value="unregister_task"/>
                                                <input type="hidden" name="exercise_id" value="${exercise.id}"/>
                                                <input type="hidden" name="task_id" value="${task.id}"/>
                                                <fmt:message key="cancelRegistration" var="lang_cancelRegistration"/>
                                                <input type="submit" class="btn btn-dark" value="${lang_cancelRegistration}"/>
                                            </form>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <c:if test="${deadline ge today}">
                                    <form name="RegisterTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                                        <input type="hidden" name="command" value="register_task"/>
                                        <input type="hidden" name="task_id" value="${task.id}"/>
                                        <fmt:message key="submitResult" var="lang_submitResult"/>
                                        <input type="submit" class="btn btn-dark" value="${lang_submitResult}"/>
                                    </form>
                                </c:if>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="container">
            <div class="row col mt-3">
                <div class="col">
                    <p><fmt:message key="taskNotFound"/></p>
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
