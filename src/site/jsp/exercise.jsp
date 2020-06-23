<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 23.06.2020
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Exercise</title>
</head>
<body>
    <c:choose>
        <c:when test="${not empty exercise}">
            <p>Student: ${exercise.student.firstname} ${exercise.student.secondname}</p>
            <p>
                <c:choose>
                    <c:when test="${exercise.grade ne '0'}">
                        Grade: ${exercise.grade}
                        Review: ${exercise.review}
                    </c:when>
                    <c:otherwise>
                        Not reviewed
                    </c:otherwise>
                </c:choose>
                <form name="CancelTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                    <input type="hidden" name="command" value="unregister_task"/>
                    <input type="hidden" name="task_id" value="${task.id}"/>
                    <input type="submit" value="Cancel registration"/>
                </form>
            </p>
            <c:if test="${sessionScope.role eq 'teacher'}">
                <form name="ReviewExerciseForm" method="post" action="${pageContext.request.contextPath}/session">
                    <input type="hidden" name="command" value="review_exercise"/>
                    <input type="hidden" name="exercise_id" value="${exercise.id}"/>
                    Comment: <input type="text" name="review" maxlength="100" required style="width: 300px; height:300px;=" value=""/>
                    Grade: <input type="number" name="grade" min="1" max="10" required/>
                    <p><input type="submit" value="Review"/></p>
                </form>
            </c:if>
        </c:when>
        <c:otherwise>
            Such exercise doesn't exist
        </c:otherwise>
    </c:choose>
</body>
</html>
