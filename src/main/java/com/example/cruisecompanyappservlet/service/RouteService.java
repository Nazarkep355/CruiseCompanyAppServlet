package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.RouteDAO;
import com.example.cruisecompanyappservlet.entity.Route;

import java.util.List;

public class RouteService {
    private RouteDAO routeDAO;
    public RouteService(){
        routeDAO = new RouteDAO();
    }
    public List<Route> findAll(){
        return routeDAO.findAll();
    }
    public Route findRouteById(Long id){
        return routeDAO.findById(id);
    }
    public boolean insertRoute(Route route){
        return routeDAO.insert(route);
    }
}
