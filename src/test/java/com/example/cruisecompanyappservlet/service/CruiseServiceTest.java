package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.CruiseDAO;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.Route;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class CruiseServiceTest {
    @Mock
    private CruiseDAO cruiseDAO;
    private CruiseService cruiseService;
    private List<Cruise> cruises;

    public CruiseServiceTest() {
        MockitoAnnotations.initMocks(this);
        cruiseService = new CruiseService(cruiseDAO);
        cruises = new ArrayList<>();
        Route route = new Route();
        for (int i = 0; i < 6; i++) {
            cruises.add(new CruiseBuilder().route(route)
                    .id(i + 1)
                    .build()
            );

        }
    }

    @Test
    void findById() {
        cruiseService.findById(15L);
        verify(cruiseDAO, times(1)).findById(15L);
        cruiseService.findById(18L);
        verify(cruiseDAO, times(1)).findById(18L);
    }

    @Test
    void getAllCruises() {
        cruiseService.getAllCruises();
        verify(cruiseDAO, times(1)).findAll();
    }

    @Test
    void getActualCruisesPaginated() {
        cruiseService.getActualCruisesPaginated(1,"asc");
        verify(cruiseDAO, times(1)).findActualPaginatedOrderByIdDesc(0,"asc");
        cruiseService.getActualCruisesPaginated(2,"asc");
        verify(cruiseDAO, times(1)).findActualPaginatedOrderByIdDesc(5,"asc");
    }

    @Test
    void getAllActualCruisesWithFreePlacesPaginated() {
        cruiseService.getAllActualCruisesWithFreePlacesPaginated(1,"asc");
        verify(cruiseDAO, times(1)).findActualWithFreePlacesOrderByIdPaginated(0,"asc");
        cruiseService.getAllActualCruisesWithFreePlacesPaginated(2, "asc");
        verify(cruiseDAO, times(1)).findActualWithFreePlacesOrderByIdPaginated(5,"asc");
    }

    @Test
    void getCruisesByCity() {
        String city = "City";
        String city2 = "City2";
        cruiseService.getCruisesByCity(city, 1,"asc");
        verify(cruiseDAO, times(1)).findActualWithFreePlacesCityLike(city, 0,"asc");
        cruiseService.getCruisesByCity(city2, 2, "asc");
        verify(cruiseDAO, times(1)).findActualWithFreePlacesCityLike(city2, 5,"asc");
    }

    @Test
    void getPaginated() {
        cruiseService.getPaginated(1,"asc");
        verify(cruiseDAO, times(1)).findAllPaginatedOrderByIdDesc(0,"asc");
        cruiseService.getPaginated(2,"asc");
        verify(cruiseDAO, times(1)).findAllPaginatedOrderByIdDesc(5, "asc");
    }

    @Test
    void insert() {
        Route route = new Route();
        Cruise cruise = new CruiseBuilder()
                .route(route)
                .build();
        cruiseService.insert(cruise);
        verify(cruiseDAO,times(1)).insert(cruise);
    }

    @Test
    void update() {
        Route route = new Route();
        Cruise cruise = new CruiseBuilder()
                .route(route)
                .build();
        cruiseService.update(cruise);
        verify(cruiseDAO,times(1)).update(cruise);

    }
}