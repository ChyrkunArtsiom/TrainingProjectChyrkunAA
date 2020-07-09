<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="createTask"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="../fragments/header.jsp"/>
</header>
<form name="CreateTaskForm" method="post" action="${pageContext.request.contextPath}/teacher/createtask">
    <input type="hidden" name="command" value="create_task"/>
    <div class="container">
        <div class="col-md-8 mt-3">
            <h1><fmt:message key="taskCreation"/></h1>
            <div class="mb-3">
                <label for="inputtask"><fmt:message key="taskName"/></label>
                <div class="input-group">
                    <input type="text" id="inputtask" name="name" required min="1" max="100" value="${name}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="startdate"><fmt:message key="startdate"/></label>
                <div class="input-group">
                    <input type="date" id="startdate" name="startdate" required value="${startdate}"/>
                </div>
            </div>
            <div class="mb-3">
                <label for="deadline"><fmt:message key="deadline"/></label>
                <div class="input-group">
                    <input type="date" id="deadline" name="deadline" required value="${deadline}"/>
                </div>
            </div>
            <div class="row">
                <div class="mb-3 col-md-3">
                    <label for="course"><fmt:message key="course"/></label>
                    <div class="input-group">
                        <select class="custom-select" id="course" name="course_id">
                            <c:forEach items="${courses}" var="course">
                                <option value="${course.id}" selected>${course.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <input type="submit" class="btn btn-dark" value="<fmt:message key="createTask"/>"/>
            <p>${sessionScope.message}</p>
            <c:remove var="message" scope="session"/>
            <p>${errorMessage}</p>
        </div>
    </div>
</form>
</body>

</html>
