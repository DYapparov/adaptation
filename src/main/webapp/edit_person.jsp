<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 04.07.2016
  Time: 12:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Person details</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="wrapper">
    <h1>Person details</h1>
    <h2><c:out value="${person.lastName}"/> <c:out value="${person.firstName}"/> <c:out value="${person.middleName}"/></h2>
    <div class = "avatar">
        <img src="<c:out value="${person.photoURL}"/>">
        <form action="change_photo.jsp" method="post">
            <input class="hidden" type="number" name="id" value="<c:out value="${person.id}"/>"/>
            <input type="submit" value="Change pic"/>
        </form>
    </div>
    <form id="edit_person_form" method="post" action="update_person" accept-charset="UTF-8">
        <table>
            <tr class="hidden"><td>id:</td><td><input type="number" name = "id" value="<c:out value="${person.id}"/>"/></td></tr>
            <tr><td>Lastname:</td> <td><input type="text" name = "lastName" value="<c:out value="${person.lastName}"/>"/></td></tr>
            <tr><td>Firstname:</td> <td><input type="text" name = "firstName" value="<c:out value="${person.firstName}"/>"/></td></tr>
            <tr><td>Middlename:</td> <td><input type="text" name = "middleName" value="${person.middleName}"/></td></tr>
            <tr><td>Post:</td> <td><input type="text" name = "post" value="<c:out value="${person.post}"/>"/></td></tr>
            <tr><td>Birthday:</td> <td><input type="date" name = "birthday" value="<fmt:formatDate value="${person.birthday}" pattern="yyyy-MM-dd"/>"/></td></tr>
        </table>
        <input type="submit" value="Save" name="action"/>
        <input type="submit" value="Delete" name="action"/>
    </form>
    <p><a href="persons">Back</a></p>

</div>
</body>
</html>
