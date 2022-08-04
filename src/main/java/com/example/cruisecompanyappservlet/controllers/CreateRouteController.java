package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.dao.RouteDAO;
import com.example.cruisecompanyappservlet.entity.Route;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.frontcontroller.Controller;
import com.example.cruisecompanyappservlet.util.RequestReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateRouteController implements Controller {
    private RouteDAO routeDAO;
    public CreateRouteController(){
        routeDAO =new RouteDAO();
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws DAOException {
        Route route = RequestReader.getRouteFromRequest(request);
        if(route==null) {
            request.getSession().setAttribute("error","PortNotFound");
            return "/controller?controller=createRoutePage";
        }
        routeDAO.insert(route);
        return "/controller";
    }

    @Override
    public AccessLevel accessLevel() {
        return AccessLevel.ADMIN;
    }
}
