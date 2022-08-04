<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 29.07.2022
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename ='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<head>
  <title><fmt:message bundle="${bundle}" key="PlanCruise"/></title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container">
  <header class="d-flex justify-content-center py-3">
    <ul class="nav nav-pills">
      <li class="nav-item"><a href="/controller" class="nav-link active" aria-current="page">
        <fmt:message bundle="${bundle}" key="Home"/></a></li>
      <li class="nav-item"><a href="/controller?controller=stations&page=1" class="nav-link">
        <fmt:message bundle="${bundle}" key="Cruises"/></a></li>
      <li class="nav-item"><a href="/controller?controller=tickets&page=1" class="nav-link">
        <fmt:message bundle="${bundle}" key="Tickets"/></a></li>
      <li class="nav-item"><form action="/controller"  method="post">
        <input type="hidden" name="controller" value="signOut">
        <button type="submit" class="nav-link"><fmt:message bundle="${bundle}" key="SignOut"/>
        </button></form></li>
    </ul>
    <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
    <p><small style="margin-top:40px;position: absolute;right: 15px">${user.getMoney()} UAH</small></p>
  </header>
</div>
<hr>
<div class="col-md-10 mx-auto col-lg-5"
     style="margin-top: 150px; min-height: 700px">
  <form action="/controller" method="post" class="p-4 p-md-5 border rounded-3 bg-light">
    <input type="hidden" name="controller" value="planCruise">
    <small class = "text-muted" style="margin-top: 15px; color: darkcyan;text-align: center" ><m:locale-tag key="AddStation"/></small>
    <div class="form-floating mb-3">
      <input type="datetime-local" name="date" class="form-control" id="floatingInput"
             placeholder="example" required ="required">
      <label for="floatingInput" ><fmt:message bundle="${bundle}" key="DepartureDate"/></label>
    </div>
    <select name="route" class="form-control" required="required">
      <option value="" ><fmt:message bundle="${bundle}" key="ChooseRoute"/></option>
      <c:forEach var="route" items="${routes}" >
        <option value="${route.getId()}" ><fmt:message bundle="${bundle}" key="Id"/>
          :   ${route.getId()}  ${route.routeToString()} </option>
      </c:forEach>
    </select>
    <div style="margin-top: 25px" class="form-floating mb-3">
      <input type="number" name="premiumCost" class="form-control" id="PremiumCostInput"
             placeholder="example" required ="required">
      <label for="PremiumCostInput"
      ><fmt:message bundle="${bundle}"
                    key="EnterCostFor"/> Premium</label>
    </div>
    <div style="margin-top: 25px" class="form-floating mb-3">
      <input type="number" name="premium" class="form-control" id="premiumInput"
             placeholder="example" required ="required">
      <label for="premiumInput"
      ><fmt:message bundle="${bundle}"
                    key="EnterCapacity"/> Premium <fmt:message
              bundle="${bundle}" key="seats"/></label>
    </div>
    <div style="margin-top: 25px" class="form-floating mb-3">
      <input type="number" name="middleCost" class="form-control" id="middleCostInput"
             placeholder="example" required ="required">
      <label for="middleCostInput"
      ><fmt:message bundle="${bundle}"
                    key="EnterCostFor"/> Middle</label>
    </div>
    <div style="margin-top: 25px" class="form-floating mb-3">
      <input type="number" name="middle" class="form-control" id="middleInput"
             placeholder="example" required ="required">
      <label for="middleInput"
      ><fmt:message bundle="${bundle}"
                    key="EnterCapacity"/> Middle <fmt:message
              bundle="${bundle}" key="seats"/></label>
    </div>
    <div style="margin-top: 25px" class="form-floating mb-3">
      <input type="number" name="economCost" class="form-control" id="economCostInput"
             placeholder="example" required ="required">
      <label for="economCostInput"
      ><fmt:message bundle="${bundle}"
                    key="EnterCostFor"/> Middle</label>
    </div>
    <div style="margin-top: 25px" class="form-floating mb-3">
      <input type="number" name="econom" class="form-control" id="economInput"
             placeholder="example" required ="required">
      <label for="economInput"
      ><fmt:message bundle="${bundle}"
                    key="EnterCapacity"/> Econom <fmt:message
              bundle="${bundle}" key="seats"/></label>
    </div>
    <button class="w-100 btn btn-lg btn-primary" style="width: 150px; margin-top: 25px"
            type="submit"><fmt:message bundle="${bundle}" key="PlanCruise"/></button>
  </form>
</div>

<footer class="py-3 my-4" style=" position: relative;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
  <ul class="nav justify-content-center border-bottom pb-3 mb-3">
    <form action="/controller" method="post">
      <input type="hidden" name="command" value="changeToUA">
      <input type="hidden" name="prev" value="/controller">
      <li class="nav-item"><button type="submit">Українська мова</button></li></form>
    <form action="/controller" method="post">
      <input type="hidden" name="command" value="changeToEn">
      <input type="hidden" name="prev" value="/controller">
      <li class="nav-item"><button type="submit">English language</button></li></form>
  </ul>
</footer>
</body>
</html>
