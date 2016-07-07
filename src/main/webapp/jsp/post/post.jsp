<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 07.07.2016
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title><c:out value="${requestScope.mode}"/> post</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="wrapper">
    <h1>Post attributes</h1>

    <c:set value="Save" var="btn"/>
    <c:if test="${requestScope.mode eq 'New'}">
        <c:set value="Add" var="btn"/>
    </c:if>

    <form id="edit_post_form" method="post" action="post" accept-charset="UTF-8">
        <table>
            <tr class="hidden"><td><input type="number" name = "id" value="<c:out value="${requestScope.post.id}"/>"/></td></tr>
            <tr><td>Post name:</td> <td><input type="text" name = "name" value="<c:out value="${requestScope.post.name}"/>" required pattern="^[A-ZА-Я][a-zа-я]{1,29}"/></td></tr>
        </table>
        <input type="submit" name = "action" value="<c:out value="${btn}"/>"/>
    </form>

    <button onclick="getElementById('confirmRemove').style.display='block'; getElementById('wrapper').style.opacity='0.2'">Delete</button>

    <p><a href="posts"><img src="img/back.png" alt="Back" class="backButton"></a></p>
</div>

<!-- Confirm window-->
<div id = "confirmRemove">
    <p>Delete <c:out value="${post.name}"/> post?</p>
    <button name="action" value="Delete" form="edit_post_form">Yes</button>
    <button onclick="getElementById('confirmRemove').style.display='none'; getElementById('wrapper').style.opacity='1'">No :(</button>
</div>

</body>
</html>
