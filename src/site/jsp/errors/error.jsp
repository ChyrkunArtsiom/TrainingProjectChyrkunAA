<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 20.05.2020
  Time: 14:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h1>PAGE IS NOT FOUND</h1>
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Message: ${requestScope['javax.servlet.error.message']}<br/>
Exception: ${pageContext.exception} <br/>
</body>
</html>
