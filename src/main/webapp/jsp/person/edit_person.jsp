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

<div id="edit_person_tab<c:out value="${person.id}"/>">
    <div id="topMenu">
        <div class = "topButton" onclick="closeTab('person', <c:out value="${person.id}"/>)">Back</div>
        <div class = "topButton" onclick="savePerson(<c:out value="${person.id}"/>)">Save</div>
        <div class = "topButton" onclick="getElementById('confirmRemove<c:out value="${person.id}"/>').style.display='block'; getElementById('edit_person_tab<c:out value="${person.id}"/>').style.opacity='0.2'">Delete</div>
        <div class = "topButton" onclick="changePhoto(<c:out value="${person.id}"/>)">Photo</div>
    </div>
    <h1>Person details</h1>
    <h2><c:out value="${person.lastName}"/> <c:out value="${person.firstName}"/> <c:out value="${person.middleName}"/></h2>
    <div class = "avatar">
        <img src="<c:out value="${person.photoURL}"/>">
    </div>
    <form id="edit_person_form" method="post" name="edit_person_form<c:out value="${person.id}"/>" action="" accept-charset="UTF-8">
        <table>
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
    </form>
</div>

<!-- Confirm window-->
<div id = "confirmRemove<c:out value="${person.id}"/>" class="confirmRemove">
    <p>Delete <c:out value="${person.lastName}"/> <c:out value="${person.firstName}"/> <c:out value="${person.middleName}"/>?</p>
    <button value="Delete" onclick="deletePerson(<c:out value="${person.id}"/>);">Yes</button>
    <button onclick="getElementById('confirmRemove<c:out value="${person.id}"/>').style.display='none'; getElementById('edit_person_tab<c:out value="${person.id}"/>').style.opacity='1'">No :(</button>
</div>
