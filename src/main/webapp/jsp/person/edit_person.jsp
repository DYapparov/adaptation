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
            <tr><td>Lastname:</td> <td><input type="text" name = "lastName" value="<c:out value="${person.lastName}"/>" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr><td>Firstname:</td> <td><input type="text" name = "firstName" value="<c:out value="${person.firstName}"/>" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr><td>Middlename:</td> <td><input type="text" name = "middleName" value="${person.middleName}" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr>
                <td>Post:</td>
                <td>
                    <select name="post" size="1">

                        <c:forEach items="${requestScope.posts}" var="post">
                            <c:set var="selected" value=""/>
                            <c:if test="${post.name eq person.post}">
                                <c:set var="selected" value="selected"/>
                            </c:if>
                            <!-- -NO MASTERS ANYMOARRRR- -->
                            <c:set var="disabled" value=""/>
                            <c:if test="${post.name eq 'Master'}">
                                <c:set var="disabled" value="disabled"/>
                            </c:if>
                            <option <c:out value="${selected}"/> <c:out value="${disabled}"/>> <c:out value="${post.name}"/> </option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr><td>Birthday:</td> <td><input type="date" name = "birthday" value="<fmt:formatDate value="${person.birthday}" pattern="yyyy-MM-dd"/>"/></td></tr>
        </table>
        <input type="submit" value="Save" name="action"/>
    </form>
    <button onclick="getElementById('confirmRemove').style.display='block'; getElementById('wrapper').style.opacity='0.2'">Delete</button>
    <p><a href="persons"><img src="img/back.png" alt="Back" class="backButton"></a></p>
</div>

<!-- Confirm window-->
<div id = "confirmRemove">
    <p>Delete <c:out value="${person.lastName}"/> <c:out value="${person.firstName}"/> <c:out value="${person.middleName}"/>?</p>
    <button name="action" value="Delete" form="edit_person_form">Yes</button>
    <button onclick="getElementById('confirmRemove').style.display='none'; getElementById('wrapper').style.opacity='1'">No :(</button>
</div>

</body>
</html>
