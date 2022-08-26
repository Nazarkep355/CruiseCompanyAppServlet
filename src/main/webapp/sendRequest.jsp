<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 30.07.2022
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<%@ page import="com.example.cruisecompanyappservlet.entity.RoomClass" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename ='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="SendRequest" /></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<header class="d-flex justify-content-center py-3">
    <ul class="nav nav-pills">
        <li class="nav-item"><a href="/controller" class="nav-link active" aria-current="page">
            <fmt:message bundle="${bundle}" key="Home"/></a></li>
        <li class="nav-item"><a href="/controller?controller=cruises&page=1" class="nav-link">
            <fmt:message bundle="${bundle}" key="Cruises"/></a></li>
        <c:if test="${isLogged}">
            <li class="nav-item"><a href="/controller?controller=tickets&page=1" class="nav-link">
                <fmt:message bundle="${bundle}" key="Tickets"/></a></li>
            <li class="nav-item"><form action="/controller"  method="post">
                <input type="hidden" name="controller" value="signOut">
                <button type="submit" class="nav-link"><fmt:message bundle="${bundle}" key="SignOut"/>
                </button></form></li></c:if>
    </ul>
    <c:if test="${isLogged}">
        <span class="fs-4" style="position: absolute;right: 15px">${userName}</span>
        <p><small style="margin-top:40px;position: absolute;right: 15px" >${userMoney} UAH</small></p>
    </c:if>
</header>
<hr>
<div>
    <div>
        <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"></use></svg>
        </a>
    </div>
</div>
<div style="min-height: 600px" class="col-md-10 mx-auto col-lg-5">
    <form action="/controller" enctype="multipart/form-data" method="post"
          class="p-4 p-md-5 border rounded-3 bg-light">
        <c:if test="${error!=null&&error.equals('NoFreePlaces')}"><small class="text-muted" style="text-align: center;color: red" >
            <fmt:message bundle="${bundle}" key="NoFreePlaces"/>  </small>
        </c:if> <input type="hidden" name="controller" value="sendRequest">
        <input type="hidden" name="id" value="${cruise.id}">
        <input type="hidden" name="roomClass" value="${roomClass}">
        <div class="mb-3">
            <label for="formFile" class="form-label"><fmt:message bundle="${bundle}" key="ChooseDoc"/></label>
            <input name="file" class="form-control" type="file" id="formFile">
        </div>
        <button  class="w-100 btn btn-lg btn-primary" type="submit">submit</button>
    </form>
</div>

<footer class="py-3 my-4" style=" position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToUA">
            <input type="hidden" name="prev"
                   value="/controller?controller=sendRequestPage&id=${cruise.id}&room=${roomClass}">
            <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev"
                   value="/controller?controller=sendRequestPage&id=${cruise.id}&room=${roomClass}">
            <li class="nav-item"><button type="submit">English language</button></li></form>
    </ul>
</footer>
</body>
</html>
