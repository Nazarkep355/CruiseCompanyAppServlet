<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 25.07.2022
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename ='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="Register" bundle="${bundle}"/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/controller" class="nav-link active" aria-current="page"><fmt:message key="Home" bundle="${bundle}"/></a></li>
            <li class="nav-item"><a href="/controller?controller=cruises&freeOnly=true&page=1" class="nav-link">
                <fmt:message bundle="${bundle}" key="Cruises"/></a></li> </ul>
    </header>
</div>

<div class="col-md-10 mx-auto col-lg-5">
    <form action="/controller" method="post" class="p-4 p-md-5 border rounded-3 bg-light">
        <input name="controller" type="hidden" value="register">
        <div class="form-floating mb-3">
            <input type="text" required="required" name="email" class="form-control" id="floatingInput" placeholder="name@example.com"
            >
            <label for="floatingInput"><fmt:message bundle="${bundle}" key="EmailAddress"/></label>
        </div>
        <div class="form-floating mb-3">
            <input type="text" name="name" required="required" class="form-control" id="floatingNameInput" placeholder="John Smith">
            <label for="floatingInput"><fmt:message bundle="${bundle}" key="Username"/></label>
        </div>
        <div class="form-floating mb-3">
            <input type="password" name="password" required="required" class="form-control"
                   id="floatingPassword" placeholder="Password" title="">
            <label for="floatingPassword"><fmt:message bundle="${bundle}" key="Password"/></label>
        </div>

        <button class="w-100 btn btn-lg btn-primary" style="width: 150px;" type="submit"><fmt:message bundle="${bundle}" key="Register"/></button>
        <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("emailInUse")}'>
            <small class="text-muted" style="margin-top:15px;color: red "><fmt:message bundle="${bundle}" key="emailInUse"/></small>
        </c:if>
        <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("EnterWrongFormat")}'>
            <small class = "text-muted" style="margin-top: 15px"><fmt:message bundle="${bundle}" key="EnterWrongFormat"/>
                <fmt:message bundle="${bundle}" key="MustBeEmailFormatAndPassword"/></small>
        </c:if>
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
            <input type="hidden" name="prev" value="/controller?controller=registerPage">
            <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/controller" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev" value="/controller?controller=registerPage">
            <li class="nav-item"><button type="submit">English language</button></li></form>
    </ul>
</footer>
</body>
</html>
