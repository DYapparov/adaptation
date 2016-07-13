<!doctype html>
<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 01.07.2016
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <link href="css/document_details.css" rel="stylesheet" type="text/css">
</head>

<div class="documentPane">
    <div class="topMenu">
        <div class = "topButton" onclick="closeTab('document', ${document.id})">Назад</div>
    </div>

    <h1>Детали поручения</h1>

    <form class="documentDetailsForm">
        <h2>${docType} №${document.registrationNumber}</h2>
        <table class = "documentDetailsTable">
            <tr class="hidden"><td>id:</td><td><input type="number" name = "id" value="${document.id}"/></td></tr>
            <tr><td>Имя документа:</td> <td><input type="text" name = "docName" value="${document.docName}"/></td></tr>
            <tr><td>Регистрационный №:</td> <td><input type="text" name = "registrationNumber" value="${document.registrationNumber}" disabled/></td></tr>
            <tr><td>Дата регистрации:</td> <td><input type="date" name = "registerDate" value="<fmt:formatDate value="${document.registerDate}" pattern="yyyy-MM-dd"/>" disabled/></td></tr>
            <tr><td>Автор:</td> <td><input type="text" name = "author" value="${document.author}" disabled/></td></tr>

            <c:choose>
                <c:when test="${requestScope.docType eq 'Task'}">
                    <tr><td>Дата получения:</td> <td><input type="date" name = "deliveryDate" value="<fmt:formatDate value="${document.deliveryDate}" pattern="yyyy-MM-dd"/>"/></td></tr>
                    <tr><td>Дата исполнения:</td> <td><input type="date" name = "finishDate" value="<fmt:formatDate value="${document.finishDate}" pattern="yyyy-MM-dd"/>"/></td></tr>
                    <tr><td>Исполнитель:</td> <td><input type="text" name = "performer" value="${document.performer}"/></td></tr>
                    <tr><td>Контрольный:</td> <td><input type="text" name = "controlTag" value="${document.controlTag}"/></td></tr>
                    <c:if test="${document.controlTag eq 'true'}">
                        <tr><td>Контроллер:</td> <td><input type="text" name = "controller" value="${document.controller}"/></td></tr>
                    </c:if>
                </c:when>
                <c:when test="${requestScope.docType eq 'Outgoing'}">
                    <tr><td>Кому:</td> <td><input type="text" name = "destination" value="${document.destination.lastName}"/></td></tr>
                    <tr><td>Способ доставки:</td> <td><input type="text" name = "deliveryMethod" value="${document.deliveryMethod}"/></td></tr>
                </c:when>
                <c:when test="${requestScope.docType eq 'Incoming'}">
                    <tr><td>От кого:</td> <td><input type="text" name = "origination" value="${document.origination}"/></td></tr>
                    <tr><td>Кому:</td> <td><input type="text" name = "destination" value="${document.destination}"/></td></tr>
                    <tr><td>Исходящий №:</td> <td><input type="number" name = "outgoingNumber" value="${document.outgoingNumber}"/></td></tr>
                    <tr><td>Исходящая дата регистрации:</td> <td><input type="date" name = "outgoingDate" value="<fmt:formatDate value="${document.outgoingDate}" pattern="yyyy-MM-dd"/>"/></td></tr>
                </c:when>
                <c:otherwise>
                    <p>Unknown document type</p>
                </c:otherwise>
            </c:choose>

            <tr><td>Текст:</td> <td><input type="text" name = "text" value="${document.text}"/></td></tr>
        </table>
    </form>
</div>