<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 13.06.2020
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Course</title>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<c:import url="header.jsp"/>
<c:if test="${not empty course}">
    <p>Name: ${course.name}</p>
    <p>Teacher: ${course.teacher.firstname} ${course.teacher.secondname}</p>
    <c:if test="${sessionScope.role eq 'teacher'}">
        <form name="CreateTaskForm" method="post" action="${pageContext.request.contextPath}/teacher/createtask">
            <input type="hidden" name="course_id" value="${course.id}"/>
            <input type="submit" value="Create task"/>
        </form>
        <br/>
        <form name="DeleteCourseForm" method="post" action="${pageContext.request.contextPath}/session">
            <input type="hidden" name="command" value="delete_course"/>
            <input type="hidden" name="course_id" value="${course.id}"/>
            <input type="submit" value="Delete Course"/>
        </form>
    </c:if>
    <c:if test="${sessionScope.role eq 'student' and registered eq 'false'}">
        <form name="RegisterForm" method="post" action="${pageContext.request.contextPath}/session">
            <input type="hidden" name="command" value="register_course"/>
            <input type="hidden" name="course_id" value="${course.id}"/>
            <input type="submit" value="Register"/>
        </form>
    </c:if>
    <c:if test="${not empty tasks}">
        Tasks:<br/>
        <table>
            <tr>
                <th>Name</th>
                <th>Startdate</th>
                <th>Deadline</th>
            </tr>
            <c:forEach items="${tasks}" var="task">
                <tr>
                    <th><a href="${pageContext.request.contextPath}/task?command=task&task_id=${task.id}">${task.name}</a></th>
                    <th>${task.startdate}</th>
                    <th>${task.deadline}</th>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</c:if>
<p>${errorMessage}</p>
<c:import url="footer.jsp"/>
</body>
</html>
