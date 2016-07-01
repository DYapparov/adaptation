<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 24.06.2016
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
    <title>Hello friend</title>
  </head>
  <body>
    <c:forEach items="${requestScope.persons}" var="person">
        <c:out value = "${person}"/>
        <br/>
    </c:forEach>
    <c:out value = "${22*11}"/>
    <br/>
    <%=System.currentTimeMillis()%>
    <br/>
    Before Gop-stop
  </body>
</html>
