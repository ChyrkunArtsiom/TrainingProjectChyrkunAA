<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Create course</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>
<form name="CreateCourseForm" method="post" action="${pageContext.request.contextPath}/session">
    <input type="hidden" name="command" value="create_course"/>
    <div class="container">
        <div class="col-md-8">
            <h1>Course creation</h1>
            <div class="mb-3">
                <label for="inputcourse">Name</label>
                <div class="input-group">
                    <input type="text" id="inputcourse" name="name" required min="1" max="45" value="${name}"/>
                </div>
            </div>
            <div class="row">
                <div class="mb-3 col-md-3">
                    <label for="teacher">Teacher</label>
                    <div class="input-group">
                        <select class="custom-select" id="teacher" name="teacher_id">
                            <c:forEach items="${teachers}" var="teacher">
                                <option value="${teacher.id}" selected>${teacher.firstname} ${teacher.secondname}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <input type="submit" class="btn btn-dark" value="Create course"/>
            <p>${errorMessage}</p>
        </div>
    </div>
</form>
</body>

</html>
