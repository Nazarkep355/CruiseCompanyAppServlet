package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.RouteDAO;
import com.example.cruisecompanyappservlet.entity.Route;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class RouteServiceTest {
    @Mock
    private RouteDAO routeDAO;
    private RouteService routeService;
    public RouteServiceTest(){
        MockitoAnnotations.openMocks(this);
        routeService = new RouteService(routeDAO);
    }

    @Test
    void findAll() {
        routeService.findAll();
        verify(routeDAO,times(1)).findAll();
    }

    @Test
    void findRouteById() {
        routeService.findRouteById(4l);
        verify(routeDAO,times(1)).findById(4l);
    }

    @Test
    void insert() {
        Route route = new Route();
        routeService.insert(route);
        verify(routeDAO,times(1)).insert(route);
    }
}