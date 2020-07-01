<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>

<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
</head>

<body>
<header>
    <c:import url="../header.jsp"/>
</header>
<h1>PAGE IS NOT FOUND</h1>
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Message: ${requestScope['javax.servlet.error.message']}<br/>
Exception: ${pageContext.exception} <br/>
</body>

</html>
