<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 01.08.2022
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<head>
    <title><fmt:message bundle="${bundle}" key="Request"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/controller" class="nav-link active" aria-current="page">
                <fmt:message bundle="${bundle}" key="Home"/></a></li>
            <li class="nav-item"><a href="/controller?controller=cruises&freeOnly=true&page=1" class="nav-link">
                <fmt:message bundle="${bundle}" key="Cruises"/></a></li>

            <c:if test="${isLogged}">
                <li class="nav-item"><a href="/controller?controller=tickets&page=1" class="nav-link">
                    <fmt:message bundle="${bundle}" key="Tickets"/></a></li>
                <li class="nav-item">
                    <form action="/" method="post">
                        <input type="hidden" name=" " value="signOut">
                        <button type="submit" class="nav-link"><fmt:message bundle="${bundle}" key="SignOut"/></button>
                    </form>
                </li>
            </c:if></ul>
        <c:if test="${isLogged}">
            <span class="fs-4" style="position: absolute;right: 15px">${userName}</span>
            <p><small style="margin-top:40px;position: absolute;right: 15px">${userMoney} UAH</small></p>
        </c:if>
    </header>
</div>
<hr>
<div>
    <div>
        <a class="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32">
                <use xlink:href="#bootstrap"></use>
            </svg>
        </a>
    </div>
</div>
<table class="table table-striped table-hover ms-5 ">
    <tbody>
    <tr>
        <td scope="row" style="text-align: center">
            <fmt:message bundle="${bundle}" key="UserBalance"/>
        </td>
        <td scope="row" style="text-align: center">${cRequest.sender.money} UAH</td>
    </tr>
    <tr>
        <td scope="row" style="text-align: center">
            <fmt:message bundle="${bundle}" key="Class"/>
        </td>
        <td scope="row" style="text-align: center">
            ${cRequest.roomClass.name()}
        </td>
        <td scope="row" style="text-align: center">
            <fmt:message bundle="${bundle}" key="Cost"/>
        </td>
        <td scope="row" style="text-align: center">
            ${cost}
        </td>
    </tr>
    </tbody>
</table>
<ul class="nav nav-pills">
    <li class="nav-item"><a href="/controller?controller=cruiseInfo&id=${cRequest.cruise.id}" class="nav-link active" aria-current="page">
        <fmt:message bundle="${bundle}" key="Cruise"/></a></li></ul>
<h4><fmt:message bundle="${bundle}" key="Document" /></h4>
 <img style="height:500px; width:500px"  alt="Document" src="images/${cRequest.photo}">
<c:if test="${cRequest.cruise.freePlaces.get(cRequest.roomClass)>0}">
    <form action="/controller" method="post">
        <input type="hidden" name="controller" value="responseRequest">
        <input type="hidden" name='response' value="true"/>
        <input type="hidden" name="id" value="${cRequest.id}">
        <button type="submit"><fmt:message bundle="${bundle}" key ="Accept"/></button>
    </form>
</c:if>
<form action="/controller" method="post">
    <input type="hidden" name="controller" value="responseRequest">
    <input type="hidden"  name="id" value="${cRequest.id}">
    <input type="hidden" name='response' value="false"/>
    <button type="submit"><fmt:message bundle="${bundle}" key ="Decline"/></button>
</form>
<footer class="py-3 my-4" style=" position: relative;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToUA">
            <input type="hidden" name="prev"
                   value="/controller?controller=requestInfo&id=${cRequest.id}">
            <li class="nav-item">
                <button type="submit">Українська мова</button>
            </li>
        </form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev"
                   value="/controller?controller=requestInfo&id=${cRequest.id}">
            <li class="nav-item">
                <button type="submit">English language</button>
            </li>
        </form>
    </ul>
</footer>
</body>
</html>
