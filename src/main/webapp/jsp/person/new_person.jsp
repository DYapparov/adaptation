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

<div>
    <div class="topMenu">
        <div class = "topButton" onclick="closeTab('newPerson', '')">Закрыть</div>
        <div class = "topButton" onclick="addPerson()">Сохранить</div>
    </div>

    <h1>Новый сотрудник</h1>

    <form class="edit_person_form" name="new_person_form" method="post" action="" accept-charset="UTF-8">
        <table>
            <tr><td>Фамилия*:</td> <td><input type="text" name = "lastName" value="" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr><td>Имя*:</td> <td><input type="text" name = "firstName" value="" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr><td>Отчество*:</td> <td><input type="text" name = "middleName" value="" required pattern="^[A-ZА-Я][a-zA-Zа-яА-Я]{1,29}"/></td></tr>
            <tr>
                <td>Должность:</td>
                <td>
                    <select name="post" size="1">
                        <c:forEach items="${requestScope.posts}" var="post">
                            <!-- -NO MASTERS ANYMOARRRR1!!11!1!!!- -->
                            <c:set var="disabled" value=""/>
                            <c:if test="${post.name eq 'Мастер'}">
                                <c:set var="disabled" value="disabled"/>
                            </c:if>
                            <option ${disabled} value="${post.id}">${post.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr><td>Дата рождения:</td> <td><input type="date" name = "birthday" value="1900-1-1"/></td></tr>
        </table>
    </form>
</div>