<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="exercise"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="fragments/header.jsp"/>
</header>
    <c:choose>
        <c:when test="${not empty exercise}">
            <div class="container">
                <div class="row mt-3">
                    <div class="col">
                        <p><fmt:message key="student"/>: ${exercise.student.firstname} ${exercise.student.secondname}</p>
                        <c:choose>
                            <c:when test="${exercise.grade ne '0'}">
                                <p><fmt:message key="grade"/>: ${exercise.grade}</p>
                                <p><fmt:message key="review"/>: ${exercise.review}</p>
                            </c:when>
                            <c:otherwise>
                                <p><fmt:message key="notReviewed"/></p>
                            </c:otherwise>
                        </c:choose>
                        <form name="CancelTaskForm" method="post" action="${pageContext.request.contextPath}/task/${exercise.task.id}">
                            <input type="hidden" name="command" value="unregister_task"/>
                            <input type="hidden" name="exercise_id" value="${exercise.id}"/>
                            <input type="hidden" name="task_id" value="${exercise.task.id}"/>
                            <fmt:message key="cancelRegistration" var="lang_cancelRegistration"/>
                            <input type="submit" class="btn btn-dark" value="${lang_cancelRegistration}"/>
                        </form>
                    </div>
                </div>
                <div class="row mt-1">
                    <div class="col">
                        <c:if test="${sessionScope.role eq 'teacher'}">
                            <form name="ReviewExerciseForm" method="post" action="${pageContext.request.contextPath}/exercise/${exercise.id}">
                                <input type="hidden" name="command" value="review_exercise"/>
                                <input type="hidden" name="exercise_id" value="${exercise.id}"/>
                                <h2><fmt:message key="reviewExercise"/></h2>
                                <div class="mb-3">
                                    <label for="inputcomment"><fmt:message key="reviewText"/></label>
                                    <div class="input-group">
                                        <input type="text" id="inputcomment" name="review" maxlength="100" value=""/>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label for="inputgrade"><fmt:message key="grade"/></label>
                                    <div class="input-group">
                                        <input type="number" id="inputgrade" name="grade" min="1" max="10" required/>
                                    </div>
                                </div>
                                <fmt:message key="estimate" var="lang_estimate"/>
                                <input type="submit" id="estimate" class="btn btn-dark" value="${lang_estimate}"/>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <fmt:message key="exerciseNotFound"/>
        </c:otherwise>
    </c:choose>
<script>
    $("ReviewExerciseForm").submit(function(){
        var message = $("#inputcomment").val();
        var encodedMsg = $('<div />').text(message).html();
        document.getElementById("inputcomment").value = encodedMsg;
    });
</script>
</body>

</html>
