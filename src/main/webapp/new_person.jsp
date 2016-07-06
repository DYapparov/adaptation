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
    <title>Person details</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="wrapper">
    <h1>Another slave</h1>

    <form id="edit_person_form" method="post" action="new_person" accept-charset="UTF-8">
        <table>
            <tr><td>Lastname:</td> <td><input type="text" name = "lastName" value=""/></td></tr>
            <tr><td>Firstname:</td> <td><input type="text" name = "firstName" value=""/></td></tr>
            <tr><td>Middlename:</td> <td><input type="text" name = "middleName" value=""/></td></tr>
            <tr><td>Post:</td> <td><input type="text" name = "post" value=""/></td></tr>
            <tr><td>Birthday:</td> <td><input type="date" name = "birthday" value="1900-1-1"/></td></tr>
        </table>
        <input type="submit" value="Sign in"/>
    </form>
    <p><a href="persons">Back</a></p>
</div>
</body>
</html>