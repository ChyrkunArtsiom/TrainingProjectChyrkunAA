<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Course</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>

<c:if test="${not empty course}">
    <div class="container">
        <div class="row col mt-3">
            <div class="col">
                <p>Name: ${course.name}</p>
                <p>Teacher: ${course.teacher.firstname} ${course.teacher.secondname}</p>
                <c:if test="${sessionScope.role eq 'admin'}">
                    <form name="DeleteCourseForm" method="post" action="${pageContext.request.contextPath}/session">
                        <input type="hidden" name="command" value="delete_course"/>
                        <input type="hidden" name="course_id" value="${course.id}"/>
                        <input type="submit" class="btn btn-dark" value="Delete Course"/>
                    </form>
                </c:if>
                <c:if test="${sessionScope.role eq 'teacher'}">
                    <form name="CreateTaskForm" method="post" action="${pageContext.request.contextPath}/teacher/createtask">
                        <input type="hidden" name="course_id" value="${course.id}"/>
                        <input type="submit" class="btn btn-dark" value="Create task"/>
                    </form>
                </c:if>
                <c:if test="${sessionScope.role eq 'student' and registered eq 'false'}">
                    <form name="RegisterForm" method="post" action="${pageContext.request.contextPath}/session">
                        <input type="hidden" name="command" value="register_course"/>
                        <input type="hidden" name="course_id" value="${course.id}"/>
                        <input type="submit" class="btn btn-dark" value="Register"/>
                    </form>
                </c:if>
                <p>${errorMessage}</p>

                <c:if test="${not empty tasks and (sessionScope.role eq 'teacher' or (sessionScope.role eq 'student' and registered ne 'false'))}">
                    <div class="container">
                        <div class="col-md-8">
                            <h1>Tasks</h1>
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Startdate</th>
                                    <th>Deadline</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${tasks}" var="task">
                                    <tr class='clickable-row' data-href='${pageContext.request.contextPath}/task?command=task&task_id=${task.id}'>
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
</c:if>

<div class="container">
    <div class="row col mt-3">
        <div class="col">
            <p>${errorMessage}</p>
        </div>
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
