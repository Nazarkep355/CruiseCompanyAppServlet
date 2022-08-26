package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Route;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.service.RouteService;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateRouteController implements Controller {
    private RouteService routeService;

    public CreateRouteController(RouteService routeService) {
        this.routeService =routeService;
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        Route route = RequestReader.getRouteFromRequest(request);
        if(route==null) {
            request.getSession().setAttribute("error","PortNotFound");
            return "redirect:/controller?controller=createRoutePage";
        }
        routeService.insert(route);
        return "redirect:/controller";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
