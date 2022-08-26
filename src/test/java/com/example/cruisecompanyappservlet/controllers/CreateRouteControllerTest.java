package com.example.cruisecompanyappservlet.controllers;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.entity.Route;
import com.example.cruisecompanyappservlet.entity.builders.RouteBuilder;
import com.example.cruisecompanyappservlet.frontcontroller.AccessLevel;
import com.example.cruisecompanyappservlet.service.RouteService;
import com.example.cruisecompanyappservlet.util.RequestReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateRouteControllerTest extends ControllerTester {
    @Mock
    private RouteService routeService;
        public CreateRouteControllerTest(){
            controller = new CreateRouteController(routeService);
        }
    @Test
    void execute() throws DAOException {
        Route route = new RouteBuilder()
                .id(1)
                .build();
        try(MockedStatic<RequestReader> mockedStatic = mockStatic(RequestReader.class)){
            mockedStatic.when(()->RequestReader.getRouteFromRequest(request)).thenReturn(route);
            String address = controller.execute(request,response);
            Assertions.assertEquals("redirect:/controller",address);
            verify(routeService,times(1)).insert(route);
            mockedStatic.when(()->RequestReader.getRouteFromRequest(request)).thenReturn(null);
            address = controller.execute(request,response);
            Assertions.assertEquals("redirect:/controller?controller=createRoutePage",address);
            verify(session,times(1)).setAttribute("error","PortNotFound");

        }
    }

    @Test
    void accessLevel() {
        Assertions.assertEquals(AccessLevel.ADMIN,controller.accessLevel());
    }
}