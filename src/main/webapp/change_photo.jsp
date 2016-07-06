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

<html>
<head>
    <title>Photo</title>
    <link href="css/index.css" rel="stylesheet" type="text/css" >
</head>
<body>
<div id="wrapper">
    <h1>Изменение фото</h1>
    <form id="set_photo_form" method="post" action="post_photo" enctype="multipart/form-data">
        <input class="hidden" type="number" name = "id" value="<c:out value="${param.id}"/>"/>
        Выберите файл:<input type="file" name = "photo" value="Browse..."/>
        <input type="submit" value="submit"/>
    </form>
    <p><a href="javascript:history.back();">Назад</a></p>
</div>
</body>
</html>
