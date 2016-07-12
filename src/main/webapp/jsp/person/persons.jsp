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

<div id="wrapper">
    <div id="topMenu">
        <div class = "topButton" onclick="newPerson()">New</div>
        <div class = "hidden topButton" id = "edit_person_button" onclick="addTab('person', selectedRow)">Edit</div>
        <div class = "hidden topButton" id = "delete_person_button" onclick="deletePerson(selectedRowId)">Delete</div>
    </div>
    <h1>List of slaves</h1>
    <table id="personsTable">
        <tr class="tableHeader">
            <th>Lastname</th>
            <th>Firstname</th>
            <th>Middlename</th>
            <th>Position</th>
        </tr>
        <c:set var="rowSwitch" value="0" scope="page"/>
        <c:forEach items="${requestScope.persons}" var="person">
            <c:set var="rowSwitch" value="${rowSwitch+1}"/>
            <tr class="<c:out value="row${rowSwitch mod 2}"/> selectable">
                <td class="hidden"><c:out value = "${person.id}"/></td>
                <td><c:out value = "${person.lastName}"/></td>
                <td><c:out value = "${person.firstName}"/></td>
                <td><c:out value = "${person.middleName}"/></td>
                <td><c:out value = "${person.post}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>

