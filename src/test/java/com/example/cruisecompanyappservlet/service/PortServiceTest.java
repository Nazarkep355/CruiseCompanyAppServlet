package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.PortDAO;
import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.builders.PortBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortServiceTest {
    @Mock
    private PortDAO portDAO;
    private PortService portService;
    private List<Port> ports;

    public PortServiceTest() {
        MockitoAnnotations.initMocks(this);
        portService = new PortService(portDAO);
        ports = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ports.add(new PortBuilder().city("City" + (i + 1))
                    .id(i + 1)
                    .build()
            );

        }
    }

    @Test
    void findPortsPaginated() {
        when(portDAO.findPortOrderByCityPaginated(0)).thenReturn(ports.subList(0, 5));
        when(portDAO.findPortOrderByCityPaginated(5)).thenReturn(ports.subList(5, 6));
        List<Port> ports1 = portService.findPortsPaginated(1);
        verify(portDAO, times(1)).findPortOrderByCityPaginated(0);
        Assertions.assertEquals("City1", ports1.get(0).getCity());
        Assertions.assertEquals("City5", ports1.get(4).getCity());
        List<Port> ports2 = portService.findPortsPaginated(2);
        verify(portDAO, times(1)).findPortOrderByCityPaginated(0);
        Assertions.assertEquals("City6", ports2.get(0).getCity());
    }

    @Test
    void isPortExists() {
        when(portDAO.findByCity("City1")).thenReturn(Optional.of(ports.get(0)));
        when(portDAO.findByCity("City6")).thenReturn(Optional.of(ports.get(5)));
        when(portDAO.findByCity("City7")).thenReturn(Optional.ofNullable(null));
        Assertions.assertTrue(portService.isPortExists("City1"));
        Assertions.assertTrue(portService.isPortExists("City6"));
        Assertions.assertFalse(portService.isPortExists("City7"));
        verify(portDAO, times(1)).findByCity("City1");
        verify(portDAO, times(1)).findByCity("City6");
        verify(portDAO, times(1)).findByCity("City7");
    }

    @Test
    void findPortByCity() {
        for (int i = 0; i < 7; i++) {
            int finalI = i;
            when(portDAO.findByCity("City"+(i+1)))
                    .thenReturn(ports.stream()
                                .filter(a->a.getCity().equals("City"+(finalI +1))).findAny());
        }
        Assertions.assertThrows(NoSuchElementException.class,
                ()->portService.findPortByCity("City7"),"No value present");
        Assertions.assertEquals(ports.get(0),portService.findPortByCity("City1"));

    }

    @Test
    void insert() {
        Port port = new PortBuilder()
                .city("City8")
                .id(8L)
                .build();
        Port port2 = new PortBuilder()
                .city("City9")
                .id(9L)
                .build();
        portService.insert(port);
        verify(portDAO,times(1)).insert(port);
        portService.insert(port);
        verify(portDAO,times(2)).insert(port);
    }
}