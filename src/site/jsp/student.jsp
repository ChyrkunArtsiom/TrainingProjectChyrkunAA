<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 14.06.2020
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Student page</title>
</head>
<body>
<c:import url="header.jsp"/>
<p><a href="${pageContext.request.contextPath}/student/courses">Show courses</a></p>
<p><a href="${pageContext.request.contextPath}/student/registered">Show registered courses</a></p>
<br/>
<c:import url="footer.jsp"/>
</body>
</html>
