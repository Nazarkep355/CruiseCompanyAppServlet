package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.Route;
import com.example.cruisecompanyappservlet.entity.Staff;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.CruiseService;
import com.example.cruisecompanyappservlet.service.RouteService;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PlanCruiseController implements Controller {
    RouteService routeService;
    CruiseService cruiseService;
    public PlanCruiseController(){
        routeService = new RouteService();
        cruiseService = new CruiseService();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        Cruise cruise = RequestReader.createCruise(request);
        cruiseService.insert(cruise);
        return "/controller?controller=cruises&actual=true&page=1";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
