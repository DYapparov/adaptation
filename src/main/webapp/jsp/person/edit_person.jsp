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

<div>
    <div class="topMenu">
        <div class = "topButton" onclick="closeTab('person', ${person.id})">Закрыть</div>
        <div class = "topButton" onclick="enableEditMode(this, ${person.id})">Изменить</div>
        <div class = "topButton" onclick="getElementById('confirmRemove${person.id}').style.display='block';">Удалить</div>
        <div class = "topButton" onclick="changePhoto(${person.id})">Фото</div>
    </div>
    <h1>Сотрудник</h1>
    <h2>${person.lastName} ${person.firstName} ${person.middleName}</h2>
    <div class = "avatar">
        <a title="Поменять фото">
            <img src="${person.photoURL}" onclick="changePhoto(${person.id})" alt="Фото">
        </a>
    </div>
    <form method="post" name="edit_person_form${person.id}" action="" accept-charset="UTF-8">
        <table>
            <tr><td>Фамилия:</td> <td><input type="text" name = "lastName" value="${person.lastName}" required pattern="^[a-zA-Zа-яА-Я]{1,30}" disabled/></td></tr>
            <tr><td>Имя:</td> <td><input type="text" name = "firstName" value="${person.firstName}" required pattern="^[a-zA-Zа-яА-Я]{1,30}" disabled/></td></tr>
            <tr><td>Отчество:</td> <td><input type="text" name = "middleName" value="${person.middleName}" required pattern="^[a-zA-Zа-яА-Я]{1,30}" disabled/></td></tr>
            <tr>
                <td>Должность:</td>
                <td>
                    <select name="post" size="1" disabled>

                        <c:forEach items="${requestScope.posts}" var="post">
                            <c:set var="selected" value=""/>
                            <c:if test="${post.id eq person.post.id}">
                                <c:set var="selected" value="selected"/>
                            </c:if>
                            <!-- -NO MASTERS ANYMOARRRR- -->
                            <c:set var="disabled" value=""/>
                            <c:if test="${post.name eq 'Мастер'}">
                                <c:set var="disabled" value="disabled"/>
                            </c:if>
                            <option ${selected} ${disabled} value="${post.id}">${post.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr><td>Дата рождения:</td> <td><input type="date" name = "birthday" value="<fmt:formatDate value="${person.birthday}" pattern="yyyy-MM-dd"/>" disabled/></td></tr>
        </table>
    </form>
</div>

<!-- Confirm window-->
<div id = 'confirmRemove${person.id}' class='confirmRemove'>
    <p>Удалить сотрудника</p>
    <p>${person.lastName} ${person.firstName} ${person.middleName}?</p>
    <button value='Delete' onclick='deletePerson(${person.id})'>Да</button>
    <button onclick="this.parentNode.style.display='none';">Нет</button>
</div>
