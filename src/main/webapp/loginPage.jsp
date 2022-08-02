<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 25.07.2022
  Time: 18:13
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
    <title><fmt:message key="Home" bundle ='${bundle}'/></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
    </script>
</head>
<body>
<div class="container">
    <header class="d-flex justify-content-center py-3">
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/controller" class="nav-link active" aria-current="page">
                <fmt:message key = "Home" bundle ='${bundle}'/>
            </a></li>
            <li class="nav-item"><a href="/controller?controller=cruises&freeOnly=true&page=1" class="nav-link">
                <fmt:message bundle="${bundle}" key="Cruises"/></a></li>
<%--            <li class="nav-item"><a href="/?command=stations&page=1" class="nav-link"><m:locale-tag key = "Stations"/></a></li>--%>
        </ul>
    </header>
</div>
<div class="container col-xl-10 col-xxl-8 px-4 py-5">
    <div class="row align-items-center g-lg-5 py-5">
        <div class="col-lg-7 text-center text-lg-start">
            <h1 class="display-4 fw-bold lh-1 mb-3"><fmt:message key="cruiseTime" bundle ='${bundle}'/></h1>
<%--            <p class="col-lg-10 fs-4"><m:locale-tag key=""/></p>--%>
        </div>
        <div class="col-md-10 mx-auto col-lg-5">
            <form action="/controller" method="post" class="p-4 p-md-5 border rounded-3 bg-light">
                <input type="hidden" name="controller" value="login">
                <div class="form-floating mb-3">
                    <input type="text" name="email" required="required" class="form-control"
                           id="floatingInput" placeholder="name@example.com">
                    <label for="floatingInput"><fmt:message key="EmailAddress" bundle ='${bundle}'/></label>
                </div>
                <div class="form-floating mb-3">
                    <input type="password" name="password" required="required" class="form-control"
                           id="floatingPassword" placeholder="Password" >

                    <label for="floatingPassword"><fmt:message key="Password" bundle ='${bundle}'/></label>
                </div>
                <button class="w-100 btn btn-lg btn-primary"  type="submit"><fmt:message key="SignUp" bundle ='${bundle}'/></button>
<%--                <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("Buy without login")}'>--%>
<%--                    <small class = "text-muted" style="margin-top: 15px"><m:locale-tag key="BeforeBuyingYou"/></small>--%>
<%--                </c:if>--%>
                <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("Wrong password")}'>
                    <small class = "text-muted" style="margin-top: 15px"><fmt:message bundle="${bundle}" key="NoUserWith"/></small>
                </c:if>
<%--                <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("EnterWrongFormat")}'>--%>
<%--                    <small class = "text-muted" style="margin-top: 15px"><m:locale-tag key="EnterWrongFormat"/></small>--%>
<%--                </c:if>--%>
                <hr class="my-4">
                <small class="text-muted"><fmt:message key="ByClickingSignUp" bundle ='${bundle}'/></small>
                <small class="text-muted"><fmt:message key="IfYouDoNotHave" bundle ='${bundle}'/><a href="/?controller=registerPage">
                    <fmt:message key="Register" bundle ='${bundle}'/></a></small>
            </form>
        </div>
    </div>
</div>
<div class="container">
    <footer class="py-3 my-4">
        <ul class="nav justify-content-center border-bottom pb-3 mb-3">
            <form action="/controller" method="post">
                <input type="hidden" name="controller" value="changeToUA">
                <input type="hidden" name="prev" value="/controller">
                <li class="nav-item"><button type="submit">Українська мова</button></li></form>
            <form action="/controller" method="post">
                <input type="hidden" name="controller" value="changeToEn">
                <input type="hidden" name="prev" value="/controller">
                <li class="nav-item"><button type="submit">English language</button></li></form></ul>
    </footer>
</div>
</body>
</html>
