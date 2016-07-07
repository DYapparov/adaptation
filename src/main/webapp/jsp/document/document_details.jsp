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

<html>
<head>
    <title>Document</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
    <div id="wrapper">
        <h1>Document details</h1>

        <form id="documentDetailsForm">
            <h2><c:out value="${docType}"/> №<c:out value="${document.registrationNumber}"/></h2>
            <table>
                <tr><td class="iarrr">id:</td><td><input type="number" name = "id" value="<c:out value="${document.id}"/>"/></td></tr>
                <tr><td class="iarrr">Document name:</td> <td><input type="text" name = "docName" value="<c:out value="${document.docName}"/>"/></td></tr>
                <tr><td class="iarrr">Registration number:</td> <td><input type="text" name = "registrationNumber" value="<c:out value="${document.registrationNumber}"/>"/></td></tr>
                <tr><td class="iarrr">Registration date:</td> <td><input type="date" name = "registerDate" value="<fmt:formatDate value="${document.registerDate}" pattern="yyyy-MM-dd"/>"/></td></tr>
                <tr><td class="iarrr">Text:</td> <td><input type="text" name = "text" value="<c:out value="${document.text}"/>"/></td></tr>
                <tr><td class="iarrr">Author:</td> <td><input type="text" name = "author" value="<c:out value="${document.author}"/>" disabled/></td></tr>

                <c:choose>
                    <c:when test="${requestScope.docType eq 'Task'}">

                        <tr><td class="iarrr">Delivery date:</td> <td><input type="date" name = "deliveryDate" value="<fmt:formatDate value="${document.deliveryDate}" pattern="yyyy-MM-dd"/>"/></td></tr>
                        <tr><td class="iarrr">Finish date:</td> <td><input type="date" name = "finishDate" value="<fmt:formatDate value="${document.finishDate}" pattern="yyyy-MM-dd"/>"/></td></tr>
                        <tr><td class="iarrr">Performer:</td> <td><input type="text" name = "performer" value="<c:out value="${document.performer}"/>"/></td></tr>
                        <tr><td class="iarrr">Control tag:</td> <td><input type="text" name = "controlTag" value="<c:out value="${document.controlTag}"/>"/></td></tr>
                        <c:if test="${document.controlTag eq 'true'}">
                            <tr><td class="iarrr">Controller:</td> <td><input type="text" name = "controller" value="<c:out value="${document.controller}"/>"/></td></tr>
                        </c:if>
                    </c:when>
                    <c:when test="${requestScope.docType eq 'Outgoing'}">
                        <tr><td class="iarrr">Destination:</td> <td><input type="text" name = "destination" value="<c:out value="${document.destination.lastName}"/>"/></td></tr>
                        <tr><td class="iarrr">Delivery method:</td> <td><input type="text" name = "deliveryMethod" value="<c:out value="${document.deliveryMethod}"/>"/></td></tr>
                    </c:when>
                    <c:when test="${requestScope.docType eq 'Incoming'}">
                        <tr><td class="iarrr">Origination:</td> <td><input type="text" name = "origination" value="<c:out value="${document.origination}"/>"/></td></tr>
                        <tr><td class="iarrr">Destination:</td> <td><input type="text" name = "destination" value="<c:out value="${document.destination}"/>"/></td></tr>
                        <tr><td class="iarrr">Outgoing number:</td> <td><input type="number" name = "outgoingNumber" value="<c:out value="${document.outgoingNumber}"/>"/></td></tr>
                        <tr><td class="iarrr">Outgoing date:</td> <td><input type="date" name = "outgoingDate" value="<fmt:formatDate value="${document.outgoingDate}" pattern="yyyy-MM-dd"/>"/></td></tr>
                    </c:when>
                    <c:otherwise>
                        <p>Unknown document type</p>
                    </c:otherwise>
                </c:choose>
            </table>
        </form>
        <p><a href="persons_documents?id=<c:out value="${document.author.id}"/>"><img src="img/back.png" alt="Back" class="backButton"></a></p>
    </div>
</body>
</html>