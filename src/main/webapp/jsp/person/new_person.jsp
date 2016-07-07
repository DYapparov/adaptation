<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 06.07.2016
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>New person</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="wrapper">
    <h1>Another slave</h1>

    <form id="edit_person_form" method="post" action="new_person" accept-charset="UTF-8">
        <table>
            <tr><td>Lastname:</td> <td><input type="text" name = "lastName" value="" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr><td>Firstname:</td> <td><input type="text" name = "firstName" value="" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr><td>Middlename:</td> <td><input type="text" name = "middleName" value="" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr>
                <td>Post:</td>
                <td>
                    <select name="post" size="1">
                        <option selected disabled>Choose...</option>

                        <c:forEach items="${requestScope.posts}" var="post">
                            <!-- -NO MASTERS ANYMOARRRR1!!11!1!!!- -->
                            <c:set var="disabled" value=""/>
                            <c:if test="${post.name eq 'Master'}">
                                <c:set var="disabled" value="disabled"/>
                            </c:if>
                            <option <c:out value="${disabled}"/>><c:out value="${post.name}"/></option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr><td>Birthday:</td> <td><input type="date" name = "birthday" value="1900-1-1"/></td></tr>
        </table>
        <input type="submit" value="Sign in"/>
    </form>
    <p><a href="persons"><img src="img/back.png" alt="Back" class="backButton"></a></p>
</div>
</body>
</html>