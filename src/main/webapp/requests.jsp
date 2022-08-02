<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 01.08.2022
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<head>
    <title><fmt:message bundle="${bundle}" key="Requests"/></title>
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
            <%--        <li class="nav-item"><a href="/?command=tickets&page=1" class="nav-link">--%>
            <%--            <fmt:message bundle="${bundle}" key="Tickets"/></a></li>--%>
            <c:if test="${isLogged}">
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
                        href="/controller?controller=requests&page=1&status=${status}&cruise=${id}">
                    <fmt:message bundle="${bundle}" key="goToFirstPage"/></a></li>
            </c:if>
            <c:if test="${page>1}">
                <li class="page-item">
                    <a class="page-link"
                       href="/controller?controller=requests&page=${page-1}&status=${status}&cruise=${id}"
                       aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${page>1}">
                <li class="page-item"><a
                        class="page-link"
                        href="/controller?controller=requests&page=${page-1}&status=${status}&cruise=${id}"
                >${page-1}</a></li>
            </c:if>

            <li class="page-item"><a
                    class="page-link"
                    href="/controller?controller=requests&page=${page}&status=${status}&cruise=${id}">${page}</a>
            </li>
            <li class="page-item"><a
                    class="page-link"
                    href="/controller?controller=requests&page=${page+1}&status=${status}&cruise=${id}">${page+1}</a>
            </li>
            <li class="page-item">
                <a class="page-link"
                   href="/controller?controller=requests&page=${page+1}&status=${status}&cruise=${id}"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
    <thead>
    <tr>
        <th scope="col"><fmt:message bundle="${bundle}" key="Status"/></th>
        <th scope="col"><fmt:message bundle="${bundle}" key="Class"/></th>
        <th scope="col"><fmt:message bundle="${bundle}" key="Route"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requests}" var="r">
        <tr style="table-layout: fixed" onclick="location.replace('/controller?controller=requestInfo&id=${r.id}')">
            <td scope="row">${r.status.name()}</td>
            <td scope="row">${r.roomClass.name()}</td>
            <td scope="row" style="max-height: 40px;max-width: 300px;overflow: auto"
            >${r.cruise.route.routeToString()}
            </td>
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
                   value="/controller?controller=requests&page=${page}&status=${status}&cruise=${cruise.id}">
            <li class="nav-item">
                <button type="submit">Українська мова</button>
            </li>
        </form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev"
                   value="/controller?controller=requests&page=${page}&status=${status}&cruise=${cruise.id}">
            <li class="nav-item">
                <button type="submit">English language</button>
            </li>
        </form>
    </ul>
</footer>
</body>
</html>
