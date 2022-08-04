<%--
  Created by IntelliJ IDEA.
  User: Quant
  Date: 04.08.2022
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>
<html lang="en">
<fmt:setLocale value='${sessionScope.get("locale")}'/>
<fmt:setBundle basename ='com.example.cruisecompanyappservlet.locale' var="bundle"/>
<head>
  <title><fmt:message bundle="${bundle}" key="CreateRoute"/></title>
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
      <c:if test="${isLogged}"><li class="nav-item"><a href="/controller?controller=tickets&page=1" class="nav-link">
        <fmt:message bundle="${bundle}" key="Tickets"/></a></li>
        <li class="nav-item"><form action="/controller"  method="post">
          <input type="hidden" name="controller" value="signOut">
          <button type="submit" class="nav-link"><fmt:message bundle="${bundle}" key="SignOut"/></button></form></li></c:if>
    </ul>
    <c:if test="${isLogged}">    <span class="fs-4" style="position: absolute;right: 15px">${user.getName()}</span>
      <p><small style="margin-top:40px;position: absolute;right: 15px" >${user.getMoney()} UAH</small></p></c:if>
  </header>
</div>
<hr>
<div style="min-height: 70%">
  <div class="col-md-10 mx-auto col-lg-5">
    <form action="/controller" method="post"  class="p-4 p-md-5 border rounded-3 bg-light">
      <input name="controller" value="createRoute" type="hidden">
      <c:if test='${error!=null&&error.equals("PortNotFound")}'>
        <small style="color: red" class="text-muted">
          <fmt:message bundle="${bundle}" key="PortNotFound"/></small></c:if>
      <div class="form-floating mb-3">
        <input onchange="resetField()"  type="number" name="portNumber" class="form-control"
               required="required" id="number input">
        <label for="number input">
          <fmt:message bundle="${bundle}" key="NumberOfPorts"/>
        </label>
      </div>
      <div id ="form">

      </div>
      <script>
        function resetField(){ const form =document.getElementById('form');
          form.innerText=''
          const numberInput = document.getElementById('number input');
          let numberOfStations = numberInput.value;
          if(numberOfStations<1)
            numberOfStations=1;
          for(let i =1;i<=numberOfStations;i++){
            const divCity =document.createElement('div')
            divCity.setAttribute('class','form-floating mb-3');
            const inputCity = document.createElement('input');
            inputCity.setAttribute('name','city'+i);
            inputCity.setAttribute('class','form-control');
            inputCity.setAttribute('required','required')
            inputCity.setAttribute('id','inputCity'+i);
            const labelCity = document.createElement('label');
            labelCity.setAttribute('for','inputCity'+i);
            labelCity.innerText='<fmt:message bundle="${bundle}" key="EnterCity"/> '+i;
            divCity.appendChild(inputCity);
            divCity.appendChild(labelCity);
            form.appendChild(divCity);
            if(i<numberOfStations){const divDelay= document.createElement('div');
              divDelay.setAttribute('class','form-floating mb-3');
              const inputDelay = document.createElement('input');
              inputDelay.setAttribute('name','delay'+i);
              inputDelay.setAttribute('class','form-control');
              inputDelay.setAttribute('required','required')
              inputDelay.setAttribute('type','number');
              inputDelay.setAttribute('id','inputDelay'+i);
              const labelDelay = document.createElement('label');
              labelDelay.setAttribute('for','inputDelay'+i);
              labelDelay.innerText='<fmt:message bundle="${bundle}" key="EnterDelay"/> '+i;
              divDelay.appendChild(inputDelay);
              divDelay.appendChild(labelDelay);
              form.appendChild(divDelay);}
          }
        }
      </script>
      <button class="w-100 btn btn-lg btn-primary"  type="submit"><fmt:message bundle="${bundle}" key="CreateRoute"/> </button>
    </form>
  </div>
</div>

<footer class="py-3 my-4" style=" position: relative;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 80px;">
  <ul class="nav justify-content-center border-bottom pb-3 mb-3">
    <form action="/controller" method="post">
      <input type="hidden" name="controller" value="changeToUA">
      <input type="hidden" name="prev" value="/controller?controller=createRoutePage">
      <li class="nav-item"><button type="submit">Українська мова</button></li></form>
    <form action="/controller" method="post">
      <input type="hidden" name="controller" value="changeToEn">
      <input type="hidden" name="prev" value="/controller?controller=createRoutePage">
      <li class="nav-item"><button type="submit">English language</button></li></form>  </ul>
</footer>
</body>
</html>
