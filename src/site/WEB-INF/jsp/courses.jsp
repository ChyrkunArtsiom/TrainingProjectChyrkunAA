<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="courses"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="fragments/header.jsp"/>
</header>

<div class="container">
    <div class="col-md-8">
        <h1><fmt:message key="courses"/></h1>
        <c:if test="${not empty courses}">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="coursename"/></th>
                    <c:if test="${sessionScope.role eq 'student' or sessionScope.role eq 'admin'}">
                        <th scope="col"><fmt:message key="teacher"/></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${courses}" var="course">
                    <tr class='clickable-row' data-href='${pageContext.request.contextPath}/course/${course.id}'>
                        <td>${course.name}</td>
                        <c:if test="${sessionScope.role eq 'student' or sessionScope.role eq 'admin'}">
                            <td>${course.teacher.firstname} ${course.teacher.secondname}</td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav>
                <ul class="pagination">
                    <c:choose>
                        <c:when test="${max_pages eq 2}">
                            <c:if test="${page eq 1}">
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}"><fmt:message key="previous"/></a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}">${page}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page+1}">${page+1}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page+1}"><fmt:message key="next"/></a></li>
                            </c:if>
                            <c:if test="${page eq 2}">
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page-1}"><fmt:message key="previous"/></a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page-1}">${page-1}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}">${page}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}"><fmt:message key="next"/></a></li>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${page eq 1 and page ne max_pages}">
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}"><fmt:message key="previous"/></a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}">${page}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page+1}">${page+1}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page+2}">${page+2}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page+1}"><fmt:message key="next"/></a></li>
                            </c:if>
                            <c:if test="${page gt 1 and page lt max_pages}">
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page-1}"><fmt:message key="previous"/></a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page-1}">${page-1}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}">${page}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page+1}">${page+1}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page+1}"><fmt:message key="next"/></a></li>
                            </c:if>
                            <c:if test="${page ne 1 and page eq max_pages}">
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page-1}"><fmt:message key="previous"/></a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page-2}">${page-2}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page-1}">${page-1}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}">${page}</a></li>
                                <li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/${role}/courses/${page}"><fmt:message key="next"/></a></li>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </nav>
        </c:if>
        <p>${sessionScope.message}</p>
        <c:remove var="message" scope="session"/>
        <p>${errorMessage}</p>
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
