<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>
<c:choose>
    <c:when test="${not empty task}">
        <div class="container">
            <div class="row col mt-3">
                <div class="col">
                    <p>Course: ${task.course.name}</p>
                    <p>Name: ${task.name}</p>
                    <p>Start date: ${task.startdate}</p>
                    <p>Deadline: ${task.deadline}</p>
                    <c:if test="${sessionScope.role eq 'teacher'}">
                        <form name="DeleteTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                            <input type="hidden" name="command" value="delete_task"/>
                            <input type="hidden" name="task_id" value="${task.id}"/>
                            <input type="submit" class="btn btn-dark" value="Delete this task"/>
                        </form>
                        <div class="container">
                            <div class="col-md-8">
                                <h1>Comepeted tasks</h1>
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>Student</th>
                                        <th>Grade</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${registrations}" var="registration">
                                        <tr class='clickable-row' data-href='${pageContext.request.contextPath}/exercise?command=exercise&exercise_id=${registration.id}'>
                                            <td>${registration.student.firstname} ${registration.student.secondname}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${registration.grade ne '0'}">${registration.grade}</c:when>
                                                    <c:otherwise>Not rated</c:otherwise>
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
                                        <p>Grade: ${exercise.grade}</p>
                                        <p>Review: ${exercise.review}</p>
                                    </c:when>
                                    <c:otherwise>
                                        <form name="CancelTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                                            <input type="hidden" name="command" value="unregister_task"/>
                                            <input type="hidden" name="task_id" value="${task.id}"/>
                                            <input type="submit" class="btn btn-dark" value="Cancel registration"/>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <form name="RegisterTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                                    <input type="hidden" name="command" value="register_task"/>
                                    <input type="hidden" name="task_id" value="${task.id}"/>
                                    <input type="submit" class="btn btn-dark" value="Register task"/>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <p>Such task doesn't exist</p>
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
