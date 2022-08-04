<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 26.07.2022
  Time: 0:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<head>
    <title><fmt:message bundle="${bundle}" key="Home"/></title>
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
                    <form action="/controller" method="post">
                        <input type="hidden" name="controller" value="signOut">
                        <button type="submit" class="nav-link"><fmt:message bundle="${bundle}" key="SignOut"/></button>
                    </form>
                </li>
            </c:if>
        </ul>
        <c:if test="${isLogged}"> <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
            <p><small style="margin-top:40px;position: absolute;right: 15px">${user.getMoney()} UAH</small></p></c:if>
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
<c:if test="${error!=null&&error.equals('NoPermission')}">
    <p><small style="margin-top:40px;margin-left: 47%; color: red"><fmt:message bundle="${bundle}"
                                                                                key="NoPermission"/></small></p>
</c:if>
<ul class="nav nav-pills flex-column mb-auto" style="margin-left: 25px; ">
    <li><a href="/controller?controller=changeMoneyPage" class="nav-link active" aria-current="page"
           style="width: 250px;margin-top: 15px">
        <svg class="bi me-2" width="16" height="16">
            <use xlink:href="#home"></use>
        </svg>
        <fmt:message bundle="${bundle}" key="ChangeMoney"/>
    </a></li>
    <li class="nav-item" style="margin-top: 15px">
        <a href="/controller?controller=ports&page=1" class="nav-link active" aria-current="page" style="width: 250px;">
            <svg class="bi me-2" width="16" height="16">
                <use xlink:href="#home"></use>
            </svg>
            <fmt:message bundle="${bundle}" key="Ports"/>
        </a>
    </li>
    <c:if test='${user.isAdmin()}'>
        <li class="nav-item" style="margin-top: 15px">
            <a href="/controller?controller=addPortPage" class="nav-link active" aria-current="page"
               style="width: 250px;">
                <svg class="bi me-2" width="16" height="16">
                    <use xlink:href="#home"></use>
                </svg>
                <fmt:message bundle="${bundle}" key="AddPort"/>
            </a>
        </li>
        <li class="nav-item" style="margin-top: 15px">
            <a href="/controller?controller=createRoutePage" class="nav-link active" aria-current="page"
               style="width: 250px;">
                <svg class="bi me-2" width="16"
                     height="16">
                    <use xlink:href="#home"></use>
                </svg>
                <fmt:message bundle="${bundle}" key="CreateRoute"/>
            </a></li>
        <li class="nav-item" style="margin-top: 15px">
            <a href="/controller?controller=requests&page=1" class="nav-link active" aria-current="page"
               style="width: 250px;">
                <svg
                        class="bi me-2" width="16" height="16">
                    <use xlink:href="#home"></use>
                </svg>
                <fmt:message bundle="${bundle}" key="Requests"/>
            </a>
        </li>
        <li class="nav-item" style="margin-top: 15px">
            <a href="/controller?controller=numOfStaff" class="nav-link active" aria-current="page"
               style="width: 250px">
                <svg class="bi me-2" width="16" height="16">
                    <use xlink:href="#home"></use>
                </svg>
                <fmt:message bundle="${bundle}" key="PlanCruise"/>
            </a></li>
    </c:if>

</ul>
<footer class="py-3 my-4" style=" position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToUA">
            <input type="hidden" name="prev" value="/controller">
            <li class="nav-item">
                <button type="submit">Українська мова</button>
            </li>
        </form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev" value="/controller">
            <li class="nav-item">
                <button type="submit">English language</button>
            </li>
        </form>
    </ul>
</footer>
</body>
</html>
