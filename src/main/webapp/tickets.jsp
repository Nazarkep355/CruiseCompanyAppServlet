<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 03.08.2022
  Time: 20:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<head>
    <title><fmt:message bundle="${bundle}" key="Tickets"/></title>
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
<table class="table table-striped table-hover" style="table-layout: fixed;text-overflow: clip">
    <nav style="margin-left: 47%" aria-label="Page navigation example">
        <ul class="pagination">
            <c:if test="${page>2}">
                <li class="page-item"><a
                        class="page-link"
                        href="/controller?controller=tickets&page=1">
                    <fmt:message bundle="${bundle}" key="goToFirstPage"/></a></li>
            </c:if>
            <c:if test="${page>1}">
                <li class="page-item"><a
                        class="page-link"
                        href="/controller?controller=tickets&page=${page-1}"
                >${page-1}</a></li>
            </c:if>

            <li class="page-item"><a
                    class="page-link"
                    href="/controller?controller=tickets&page=${page}">${page}</a>
            </li>
            <c:if test="${!max}">
                <li class="page-item"><a
                        class="page-link"
                        href="/controller?controller=tickets&page=${page+1}">${page+1}</a>
                </li>
            </c:if>
        </ul>
    </nav>
    <thead>
    <tr>
        <th scope="col"><fmt:message bundle="${bundle}" key="DepartureDate"/></th>
        <th scope="col"><fmt:message bundle="${bundle}" key="Class"/></th>
        <th scope="col"><fmt:message bundle="${bundle}" key="Route"/></th>
        <th scope="col"><fmt:message bundle="${bundle}" key="ReturningDate"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tickets}" var="ticket">
        <tr style="table-layout: fixed"
            onclick="location.replace('/controller?controller=cruiseInfo&id=${ticket.cruise.getId()}')">
            <td scope="row">${ticket.cruise.schedule.get(ticket.cruise.route.ports.get(0))}</td>
            <td scope="row">${ticket.roomClass}</td>
            <td scope="row" style="max-height: 40px;max-width: 300px;overflow: auto"
            >${ticket.cruise.route.routeToString()}
            </td>
            <td scope="row">${ticket.cruise.schedule.get(ticket.cruise.route.ports.get(ticket.cruise.route.ports.size()-1))}</td>
        </tr>
    </c:forEach>
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
                   value="/controller?controller=tickets&page=${page}">
            <li class="nav-item">
                <button type="submit">???????????????????? ????????</button>
            </li>
        </form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev"
                   value="/controller?controller=tickets&page=${page}">
            <li class="nav-item">
                <button type="submit">English language</button>
            </li>
        </form>
    </ul>
</footer>

</body>
</html>
