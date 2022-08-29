<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 27.07.2022
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<%@ taglib uri="/WEB-INF/MyCruiseTagLib.tld" prefix="m" %>
<head>
    <title><fmt:message bundle="${bundle}" key="Cruises"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/controller" class="nav-link active" aria-current="page">
                <fmt:message bundle="${bundle}" key="Home"/></a></li>
            <li class="nav-item"><a href="/controller?controller=cruises&page=1" class="nav-link">
                <fmt:message bundle="${bundle}" key="Cruises"/></a></li>
            <c:if test="${isLogged}">
                <li class="nav-item"><a href="/controller?controller=tickets&page=1" class="nav-link">
                    <fmt:message bundle="${bundle}" key="Tickets"/></a></li>
                <li class="nav-item">
                    <form action="/controller" method="post">
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
<form action="/controller" method="get" class="row gx-3 gy-2 align-items-center mx-auto">
    <input type="hidden" name="page" value="1">
    <input type="hidden" name="controller" value="cruises">
    <div class="col-sm-3">
        <label class="visually-hidden" for="City">Name</label>
        <input type="text" name="city" class="form-control" id="City"
               placeholder="<fmt:message bundle="${bundle}" key="City"/>">
    </div>
    <div>
        <input type="checkbox" name="freeOnly" class="form-check-input mt-0" id="freeOnly">
        <label for="freeOnly"><fmt:message bundle="${bundle}" key="OnlyFreePlaces"/></label>
    </div>
    <div>
        <input type="checkbox" name="actual" class="form-check-input mt-0" id="actualOnly">
        <label for="actualOnly"><fmt:message bundle="${bundle}" key="ActualOnly"/></label>
    </div>
    <div class="col-sm-3">
        <button type="submit" class="btn btn-primary"><fmt:message bundle="${bundle}" key="Find"/></button>
    </div>
</form>

<table class="table table-striped table-hover" style="table-layout: fixed;text-overflow: clip">
    <nav style="margin-left: 47%" aria-label="Page navigation example">
        <ul class="pagination">
            <c:if test="${page>2}">
                <li class="page-item"><a
                        class="page-link"
                        href="/controller?controller=cruises&page=1&freeOnly=${freeOnly}&actual=${actual}&city=${city}&order=${order}">
                    <fmt:message bundle="${bundle}" key="goToFirstPage"/></a></li>
            </c:if>
            <c:if test="${page>1}">
                <li class="page-item"><a
                        class="page-link"
                        href="/controller?controller=cruises&page=${page-1}&freeOnly=${freeOnly}&actual=${actual}&city=${city}&order=${order}"
                >${page-1}</a></li>
            </c:if>
            <li class="page-item"><a
                    class="page-link"
                    href="/controller?controller=cruises&page=${page}&freeOnly=${freeOnly}&actual=${actual}&city=${city}&order=${order}">${page}</a>
            </li>
            <c:if test="${!max}">
                <li class="page-item"><a
                        class="page-link"
                        href="/controller?controller=cruises&page=${page+1}&freeOnly=${freeOnly}&actual=${actual}&city=${city}&order=${order}"
                >${page+1}</a>
                </li>
            </c:if>
        </ul>
    </nav>
    <p>
        <select onchange="location = this.value" >
            <option><fmt:message bundle="${bundle}" key="Order"/></option>
            <option value="/controller?controller=cruises&page=${page}&freeOnly=${freeOnly}&actual=${actual}&city=${city}&order=asc">
             <fmt:message bundle="${bundle}" key="FromNewToOld"/></option>
            <label for="descOrder"></label>
            <option
                    value="/controller?controller=cruises&page=${page}&freeOnly=${freeOnly}&actual=${actual}&city=${city}&order=desc">
                <fmt:message bundle="${bundle}" key="FromOldToNew"/></option>
        </select>
    </p>
    <thead>
    <tr>
        <th scope="col"><fmt:message bundle="${bundle}" key="DepartureDate"/></th>
        <th scope="col"><fmt:message bundle="${bundle}" key="daysInJourney"/></th>
        <th scope="col"><fmt:message bundle="${bundle}" key="Route"/></th>
    </tr>
    </thead>
    <tbody>
    <m:cruises></m:cruises>
    </tbody>
</table>
<footer class="py-3 my-4" style=" position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToUA">
            <input type="hidden" name="prev"
                   value="/controller?controller=cruises&page=${page}&freeOnly=${freeOnly}&actual=${actual}&city=${city}">
            <li class="nav-item">
                <button type="submit">Українська мова</button>
            </li>
        </form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev"
                   value="/controller?controller=cruises&page=${page}&freeOnly=${freeOnly}&actual=${actual}&city=${city}">
            <li class="nav-item">
                <button type="submit">English language</button>
            </li>
        </form>
    </ul>
</footer>
</body>
</html>
