<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 08.08.2022
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename ='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<html>
<head>
    <title><fmt:message bundle="${bundle}" key="AddPort"/> </title>
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
            <li class="nav-item"><a href="/controller?controller=tickets&page=1" class="nav-link">
                <fmt:message bundle="${bundle}" key="Tickets"/></a></li>
            <li class="nav-item"><form action="/controller"  method="post">
                <input type="hidden" name="controller" value="signOut">
                <button type="submit" class="nav-link"><fmt:message bundle="${bundle}" key="SignOut"/></button></form></li>
        </ul>
        <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
        <p><small style="margin-top:40px;position: absolute;right: 15px" >${user.getMoney()} UAH</small></p>
    </header>
</div>
<hr>


<div class="col-md-10 mx-auto col-lg-5"
     style="margin-top: 150px">
    <form action="/controller" method="post" class="p-4 p-md-5 border rounded-3 bg-light">
        <input name="controller" value="changeMoney" type="hidden">
        <small class = "text-muted" style="margin-top: 15px; color: darkcyan;text-align: center"><fmt:message bundle="${bundle}" key="AddPort"/></small>
        <div class="form-floating mb-3">
            <input type="number" name="money" class="form-control" id="floatingInput" placeholder="example">
            <label for="floatingInput"><fmt:message bundle="${bundle}" key="EnterAmount"/></label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" style="width: 150px;" type="submit">
            <fmt:message bundle="${bundle}" key="ChangeMoney"/>
        </button>
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
            <input type="hidden" name="prev" value="/controller?controller=changeMoneyPage">
            <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev" value="/controller?controller=changeMoneyPage">
            <li class="nav-item"><button type="submit">English language</button></li></form>  </ul>
</footer>
</body>
</html>
