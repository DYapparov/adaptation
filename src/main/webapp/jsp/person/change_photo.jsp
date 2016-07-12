<%--
  Created by IntelliJ IDEA.
  User: dyapparov
  Date: 06.07.2016
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="wrapper">
    <div id="topMenu">
        <div class = "topButton" onclick="reloadTab('person', <c:out value="${param.id}"/>)">Back</div>
        <div class = "topButton" onclick="savePhoto(<c:out value="${param.id}"/>)">Save</div>
    </div>
    <h1>Изменение фото</h1>
    <form id="set_photo_form" name="set_photo_form" method="post" action="" enctype="multipart/form-data">
        <input class="hidden" type="number" name = "id" value="<c:out value="${param.id}"/>"/>
        Выберите файл:<input type="file" name = "photo" value="Browse..."/>
    </form>
</div>
