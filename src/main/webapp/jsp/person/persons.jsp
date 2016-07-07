<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 01.07.2016
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Slaves</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
    <div id="wrapper">
        <h1>List of slaves</h1>
        <table class="listTable">
            <tr class="tableHeader">
                <th>Lastname</th>
                <th>Firstname</th>
                <th>Middlename</th>
                <th>Position</th>
                <th>View</th>
            </tr>
            <c:set var="rowSwitch" value="0" scope="page"/>
            <tr class="<c:out value="row${rowSwitch mod 2}"/> selectable">
            <td><a href="new_person">New slave</a></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            </tr>
            <c:forEach items="${requestScope.persons}" var="person">
                <c:set var="rowSwitch" value="${rowSwitch+1}"/>
                <tr class="<c:out value="row${rowSwitch mod 2}"/> selectable">
                    <td><c:out value = "${person.lastName}"/></td>
                    <td><c:out value = "${person.firstName}"/></td>
                    <td><c:out value = "${person.middleName}"/></td>
                    <td><c:out value = "${person.post}"/></td>
                    <td class="detailsButton">
                        <a href="edit_person?id=<c:out value = "${person.id}"/>"title="Details"><img src="img/details.png" alt="Details" width="32"/></a>
                        <a href="persons_documents?id=<c:out value="${person.id}"/>" title="Documents"><img src="img/documents.png" alt="Documents" width="32"/></a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <p><a href="."><img src="img/back.png" alt="Back" class="backButton"></a></p>
    </div>
</body>
</html>
