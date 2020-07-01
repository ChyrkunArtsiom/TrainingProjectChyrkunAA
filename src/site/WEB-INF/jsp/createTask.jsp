<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Create task</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>
<form name="CreateTaskForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_task"/>
    <input type="hidden" name="course_id" value="${param.course_id}"/>
    <div class="container">
        <div class="col-md-8 mt-3">
            <h1>Task creation</h1>
            <div class="mb-3">
                <label for="inputtask">Name</label>
                <div class="input-group">
                    <input type="text" id="inputtask" name="name" required min="1" max="100" value="${name}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="startdate">Startdate</label>
                <div class="input-group">
                    <input type="date" id="startdate" name="startdate" required value="${startdate}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="deadline">Deadline</label>
                <div class="input-group">
                    <input type="date" id="deadline" name="deadline" required value="${deadline}"/>
                </div>
            </div>
            <input type="submit" class="btn btn-dark" value="Create task"/>
            <p>${sessionScope.message}</p>
            <c:remove var="message" scope="session"/>
            <p>${errorMessage}</p>
        </div>
    </div>
</form>
</body>

</html>
