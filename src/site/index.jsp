<%--
  Created by IntelliJ IDEA.
  User: Tarakanio
  Date: 16.03.2020
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Test page</title>
  </head>
  <body>
  <h1>Hello World!</h1>
  Absolute Path is:<%= pageContext.getServletContext().getRealPath("/")%>
  </body>
</html>
