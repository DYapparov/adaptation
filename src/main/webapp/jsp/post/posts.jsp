<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 07.07.2016
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Posts</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="wrapper">
    <h1>Posts</h1>
    <table class="listTable">
        <tr class="tableHeader">
            <th>Name</th>
            <th>View</th>
        </tr>
        <c:set var="rowSwitch" value="0" scope="page"/>
        <tr class="<c:out value="row${rowSwitch mod 2}"/> selectable">
            <td><a href="post">New post</a></td>
            <td></td>
        </tr>
        <c:forEach items="${requestScope.posts}" var="post">
            <c:set var="rowSwitch" value="${rowSwitch+1}"/>
            <tr class="<c:out value="row${rowSwitch mod 2}"/> selectable">
                <td><c:out value = "${post.name}"/></td>
                <td class="detailsButton"><a href="<c:out value="post?id=${post.id}"/>" title="Details"><img src="img/details.png" alt="Edit" width="32"/></a></td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="."><img src="img/back.png" alt="Back" class="backButton"></a></p>
</div>
</body>
</html>

