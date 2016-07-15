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


<div class="topMenu">
    <div class = "topButton" onclick="newPerson()">Добавить</div>
    <div class = "hidden topButton" id = "edit_person_button" onclick="addTab('person', selectedRowId)">Детали</div>
    <div class = "hidden topButton" id = "delete_person_button" onclick="deletePerson(selectedRowId)">Удалить</div>
</div>
<h1>Список сотрудников</h1>
<div id="personsWrap">
    <table id="personsTable">
        <thead>
        <tr class="tableHeader">
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Должность</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="rowSwitch" value="0" scope="page"/>
        <c:forEach items="${requestScope.persons}" var="person">
            <c:set var="rowSwitch" value="${rowSwitch+1}"/>
            <tr class="row${rowSwitch mod 2} selectable" ondblclick="addTab('person', ${person.id})">
                <td class="hidden">${person.id}</td>
                <td>${person.lastName}</td>
                <td>${person.firstName}</td>
                <td>${person.middleName}</td>
                <td>${person.post.name}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

