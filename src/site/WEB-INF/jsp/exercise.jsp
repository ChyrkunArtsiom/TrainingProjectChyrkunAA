<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Exercise</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="header.jsp"/>
</header>
    <c:choose>
        <c:when test="${not empty exercise}">
            <div class="container">
                <div class="row mt-3">
                    <div class="col">
                        <p>Student: ${exercise.student.firstname} ${exercise.student.secondname}</p>
                        <c:choose>
                            <c:when test="${exercise.grade ne '0'}">
                                <p>Grade: ${exercise.grade}</p>
                                <p>Review: ${exercise.review}</p>
                            </c:when>
                            <c:otherwise>
                                <p>Not reviewed</p>
                            </c:otherwise>
                        </c:choose>
                        <form name="CancelTaskForm" method="post" action="${pageContext.request.contextPath}/session">
                            <input type="hidden" name="command" value="unregister_task"/>
                            <input type="hidden" name="exercise_id" value="${exercise.id}"/>
                            <input type="submit" class="btn btn-dark" value="Cancel registration"/>
                        </form>
                    </div>
                </div>
                <div class="row mt-1">
                    <div class="col">
                        <c:if test="${sessionScope.role eq 'teacher'}">
                            <form name="ReviewExerciseForm" method="post" action="${pageContext.request.contextPath}/session">
                                <input type="hidden" name="command" value="review_exercise"/>
                                <input type="hidden" name="exercise_id" value="${exercise.id}"/>

                                <h2>Review exercise</h2>
                                <div class="mb-3">
                                    <label for="inputcomment">Name</label>
                                    <div class="input-group">
                                        <input type="text" id="inputcomment" name="review" maxlength="100" value=""/>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="inputgrade">Grade</label>
                                    <div class="input-group">
                                        <input type="number" id="inputgrade" name="grade" min="1" max="10" required/>
                                    </div>
                                </div>
                                <input type="submit" class="btn btn-dark" value="Review"/>

                            </form>
                        </c:if>
                    </div>

                </div>
            </div>
        </c:when>
        <c:otherwise>
            Such exercise doesn't exist
        </c:otherwise>
    </c:choose>
</body>

</html>
