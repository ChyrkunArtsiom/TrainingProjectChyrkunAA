<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 16.06.2020
  Time: 11:05
  To change this template use File | Settings | File Templates.
--%>
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
        <p>Course: ${task.course.name}</p>
        <p>Name: ${task.name}</p>
        <p>Start date: ${task.startdate}</p>
        <p>Deadline: ${task.deadline}</p>
        <c:if test="${sessionScope.role eq 'teacher'}">
            <form name="DeleteTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                <input type="hidden" name="command" value="delete_task"/>
                <input type="hidden" name="task_id" value="${task.id}"/>
                <input type="submit" value="Delete this task"/>
            </form>

            <c:if test="${not empty registrations}">
                Completed by students tasks:<br/>
                <table>
                    <tr>
                        <th>Student</th>
                        <th>Grade</th>
                    </tr>
                    <c:forEach items="${registrations}" var="registration">
                        <tr>
                            <th><a href="${pageContext.request.contextPath}/exercise?command=exercise&exercise_id=${registration.id}">${registration.student.firstname} ${registration.student.secondname}</a></th>
                            <th>
                                <c:choose>
                                    <c:when test="${registration.grade ne '0'}">${registration.grade}</c:when>
                                    <c:otherwise>Not rated</c:otherwise>
                                </c:choose>
                            </th>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
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
                                <input type="submit" value="Cancel registration"/>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <form name="RegisterTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                        <input type="hidden" name="command" value="register_task"/>
                        <input type="hidden" name="task_id" value="${task.id}"/>
                        <input type="submit" value="Register task"/>
                    </form>
                </c:otherwise>
            </c:choose>
        </c:if>
    </c:when>
    <c:otherwise>
        Such task doesn't exist
    </c:otherwise>
</c:choose>
<footer>
    <c:import url="footer.jsp"/>
</footer>
</body>
</html>
