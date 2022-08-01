<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 30.07.2022
  Time: 13:26
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
    <title><fmt:message bundle="${bundle}" key="CruiseInfo" /></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
</head>
<body>


<header class="d-flex justify-content-center py-3">
    <ul class="nav nav-pills">
        <li class="nav-item"><a href="/" class="nav-link active" aria-current="page">
            <fmt:message bundle="${bundle}" key="Home"/></a></li>
        <li class="nav-item"><a href="/?controller=stations&page=1" class="nav-link">
            <fmt:message bundle="${bundle}" key="Cruises"/></a></li>
        <c:if test="${isLogged}">    <li class="nav-item"><a href="/?command=tickets&page=1" class="nav-link"><m:locale-tag key="Tickets"/></a></li>
        <li class="nav-item"><form action="/"  method="post">
            <input type="hidden" name="controller" value="signOut">
            <button type="submit" class="nav-link"><fmt:message bundle="${bundle}" key="SignOut"/>
            </button></form></li></c:if>
    </ul>
    <c:if test="${isLogged}">
        <span class="fs-4" style="position: absolute;right: 15px">${userName}</span>
        <p><small style="margin-top:40px;position: absolute;right: 15px" >${userMoney} UAH</small></p>
    </c:if>
</header>
</div>
<hr>

<div style="min-height: 70%">
<%--    <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("notEnoughFunds")}'>--%>
<%--        <small class = "text-muted" style="margin-left: 40%"><m:locale-tag key="notEnoughFunds"/></small>--%>
<%--    </c:if>--%>

<%--    <c:if test='${requestScope.get("error")!=null&&requestScope.get("error").equals("NoFreeSeats")}'>--%>
<%--        <small class = "text-muted" style="margin-left: 40%"><m:locale-tag key="NoFreeSeats"/></small>--%>
<%--    </c:if>--%>
    <div style="margin-left: 50px">
        <h5><i style="color: red">Premium</i> <fmt:message bundle="${bundle}" key="Cost"/>:  ${cruise.getCostPremium()} </h5>
        <h5><fmt:message bundle="${bundle}" key='FreePlaces'/>: ${cruise.freePlaces.get(RoomClass.PREMIUM)}</h5>
        <c:if test="${isLogged}"><a href="/?controller=sendRequestPage&id=${cruise.id}&room=PREMIUM">buy Premium</a></c:if>
    </div>
    <div style="margin-left: 50px">
        <h5><i style="color: darkblue">MIDDLE</i> <fmt:message bundle="${bundle}" key="Cost"/>:  ${cruise.getCostMiddle()} </h5>
        <h5><fmt:message bundle="${bundle}" key='FreePlaces'/>: ${cruise.freePlaces.get(RoomClass.MIDDLE)}</h5>
        <c:if test="${isLogged}"><a href="/?controller=sendRequestPage&id=${cruise.id}&room=MIDDLE">buy MIDDLE</a></c:if>
    </div>
    <div style="margin-left: 50px">
        <h5><i style="color: gray">ECONOM</i> <fmt:message bundle="${bundle}" key="Cost"/>:  ${cruise.getCostEconom()} </h5>
        <h5><fmt:message bundle="${bundle}" key='FreePlaces'/>: ${cruise.freePlaces.get(RoomClass.ECONOM)}</h5>
        <c:if test="${isLogged}"><a href="/?controller=sendRequestPage&id=${cruise.id}&room=ECONOM">buy ECONOM</a></c:if>
    </div>
    <table class="table table-striped table-hover ms-5 " style="max-width: 500px">
        <thead>
        <tr>
            <th scope="col" style="text-align: center"><fmt:message bundle="${bundle}" key="City"/></th>
            <th scope="col" style="text-align: center"><fmt:message bundle="${bundle}" key="DepartureDate"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="port" items="${cruise.route.ports}">
        <tr>
            <td scope="row" style="text-align: center">${port.city}</td>
            <td scope="row" style="text-align: center">${cruise.schedule.get(port)}</td>
        </tr>
        </c:forEach>


</tbody>
</table>
<%--<c:if test='${user!=null&&user.isAdmin()}'>--%>
<%--    <form  style="width: 300px;padding-left: 150px;" action="/" method="post">--%>
<%--        <input name="command" type="hidden" value="cancelTrain">--%>
<%--        <input type="hidden" name="trainId" value="${train.getId()}">--%>
<%--        <button type="submit"  class="w-100 btn btn-lg btn-primary"><m:locale-tag key="CancelTrain"/></button>--%>
<%--    </form>--%>
<%--</c:if>--%>
<%--<ul style="position: absolute;margin-left: 1000px" class="nav nav-pills">--%>
<%--    <c:if test="${station1==null}">--%>
<%--        <form action="/" method="post">--%>
<%--            <input name="command" type="hidden" value="buyOne">--%>
<%--            <input type="hidden" name="trainId" value="${train.getId()}">--%>
<%--            <button  type="submit" style="width: 250px" class="w-100 btn btn-lg btn-primary"><m:locale-tag key="Buy"/></button>--%>
<%--        </form>--%>
<%--    </c:if>--%>
<%--    <c:if test="${station1!=null}">--%>
        <form action="/" method="post">
            <input name="command" type="hidden" value="buyOne">
            <input type="hidden" name="from" value="${station1}">
            <input type="hidden" name="to" value="${station2}">
            <input type="hidden" name="trainId" value="${train.getId()}">
            <button  type="submit" style="width: 250px" class="w-100 btn btn-lg btn-primary">
                <fmt:message bundle="${bundle}" key="Buy"/></button>
        </form>
<%--    </c:if>--%>
<%--</ul>--%>

</div>

<footer class="py-3 my-4" style=" position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
    <ul class="nav justify-content-center border-bottom pb-3 mb-3">
        <form action="/" method="post">
            <input type="hidden" name="controller" value="changeToUA">
            <input type="hidden" name="prev"
                   value="/?controller=cruiseInfo&id=${cruise.id}">
              <li class="nav-item"><button type="submit">Українська мова</button></li></form>
        <form action="/" method="post">
            <input type="hidden" name="controller" value="changeToEn">
            <input type="hidden" name="prev"
                   value="/?controller=cruiseInfo&id=${cruise.id}">
            <li class="nav-item"><button type="submit">English language</button></li></form>
    </ul>
</footer>
</body>
</html>
