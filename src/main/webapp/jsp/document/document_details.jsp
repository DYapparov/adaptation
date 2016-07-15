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
        <div class = "topButton" onclick="closeTab('document', ${document.id})">Закрыть</div>
    </div>

    <h1>Детали поручения</h1>

    <form class="documentDetailsForm">
        <h2>${docType} №${document.registrationNumber}</h2>
        <table class = "documentDetailsTable">
            <tr class="hidden"><td>id:</td><td><input type="number" name = "id" value="${document.id}"/></td></tr>
            <tr><td>Имя документа:</td> <td class="leftAlign"><input type="text" name = "docName" value="${document.docName}" disabled/></td></tr>
            <tr><td>Регистрационный №:</td> <td class="leftAlign"><input type="text" name = "registrationNumber" value="${document.registrationNumber}" disabled/></td></tr>
            <tr><td>Дата регистрации:</td> <td class="leftAlign"><input type="date" name = "registerDate" value="<fmt:formatDate value="${document.registerDate}" pattern="yyyy-MM-dd"/>" disabled/></td></tr>
            <tr><td>Автор:</td> <td class="leftAlign"><input type="text" name = "author" value="${document.author.lastName} ${document.author.firstName} ${document.author.middleName}, ${document.author.post.name}" disabled/></td></tr>

            <c:choose>
                <c:when test="${requestScope.docType eq 'Task'}">
                    <tr><td>Дата получения:</td> <td class="leftAlign"><input type="date" name = "deliveryDate" value="<fmt:formatDate value="${document.deliveryDate}" pattern="yyyy-MM-dd"/>" disabled/></td></tr>
                    <tr><td>Дата исполнения:</td> <td class="leftAlign"><input type="date" name = "finishDate" value="<fmt:formatDate value="${document.finishDate}" pattern="yyyy-MM-dd"/>" disabled/></td></tr>
                    <tr><td>Исполнитель:</td> <td class="leftAlign"><input type="text" name = "performer" value="${document.performer.lastName} ${document.performer.firstName} ${document.performer.middleName}, ${document.performer.post.name}" disabled/></td></tr>
                    <tr><td>Контрольный:</td> <td class="leftAlign"><input type="text" name = "controlTag" value="${document.controlTag}" disabled/></td></tr>
                    <c:if test="${document.controlTag eq 'true'}">
                        <tr><td>Контроллер:</td> <td class="leftAlign"><input type="text" name = "controller" value="${document.controller.lastName} ${document.controller.firstName} ${document.controller.middleName}, ${document.controller.post.name}" disabled/></td></tr>
                    </c:if>
                </c:when>
                <c:when test="${requestScope.docType eq 'Outgoing'}">
                    <tr><td>Кому:</td> <td class="leftAlign"><input type="text" name = "destination" value="${document.destination.lastName} ${document.destination.firstName} ${document.destination.middleName}, ${document.destination.post.name}" disabled/></td></tr>
                    <tr><td>Способ доставки:</td> <td class="leftAlign"><input type="text" name = "deliveryMethod" value="${document.deliveryMethod}" disabled/></td></tr>
                </c:when>
                <c:when test="${requestScope.docType eq 'Incoming'}">
                    <tr><td>От кого:</td> <td class="leftAlign"><input type="text" name = "origination" value="${document.origination.lastName} ${document.origination.firstName} ${document.origination.middleName}, ${document.origination.post.name}" disabled/></td></tr>
                    <tr><td>Кому:</td> <td class="leftAlign"><input type="text" name = "destination" value="${document.destination.lastName} ${document.destination.firstName} ${document.destination.middleName}, ${document.destination.post.name}" disabled/></td></tr>
                    <tr><td>Исходящий №:</td> <td class="leftAlign"><input type="number" name = "outgoingNumber" value="${document.outgoingNumber}" disabled/></td></tr>
                    <tr><td>Исходящая дата регистрации:</td> <td class="leftAlign"><input type="date" name = "outgoingDate" value="<fmt:formatDate value="${document.outgoingDate}" pattern="yyyy-MM-dd"/>" disabled/></td></tr>
                </c:when>
                <c:otherwise>
                    <p>Unknown document type</p>
                </c:otherwise>
            </c:choose>

            <tr><td colspan="2" class="leftAlign">Текст:</td></tr>
            <tr><td colspan="2" class="leftAlign"><textarea name = "text" cols="48" rows="5" disabled>${document.text}</textarea></td></tr>
        </table>
    </form>
</div>