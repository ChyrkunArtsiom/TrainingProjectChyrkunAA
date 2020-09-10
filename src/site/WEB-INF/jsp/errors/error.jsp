<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="text"/>
<html>

<head>
    <title><fmt:message key="pageNotFound"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="../fragments/header.jsp"/>
</header>
<h1><fmt:message key="pageNotFound"/></h1>
<p class="list-group">
    <p><fmt:message key="somethingHasGoneWrong"/></p>
</div>
<%--
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Message: ${requestScope['javax.servlet.error.message']}<br/>
Exception: ${pageContext.exception} <br/>--%>
</body>

</html>
