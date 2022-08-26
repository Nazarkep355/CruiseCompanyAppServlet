package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.StaffDAO;
import com.example.cruisecompanyappservlet.entity.Staff;
import com.example.cruisecompanyappservlet.entity.builders.StaffBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTest {
    private StaffService staffService;
    @Mock
    private StaffDAO staffDAO;
    public StaffServiceTest(){
        MockitoAnnotations.initMocks(this);
        staffService = new StaffService(staffDAO);
    }

    @Test
    void findById() {
        staffService.findById(4l);
        verify(staffDAO,times(1)).findById(4l);
    }

    @Test
    void isStaffWithNameExists() {
        String name1 = "Jack";
        String name2 = "John";
        when(staffDAO.isStaffWithNameExists(name1)).thenReturn(true);
        when(staffDAO.isStaffWithNameExists(name2)).thenReturn(false);
        Assertions.assertTrue(staffService.isStaffWithNameExists(name1));
        Assertions.assertFalse(staffService.isStaffWithNameExists(name2));
    }

    @Test
    void insert() {
        String name = "Jack";
        String pos = "Captain";
        Staff staff = new StaffBuilder()
                .name(name)
                .position(pos)
                .build();
        staffService.insert(name,pos);
        verify(staffDAO,times(1)).insert(staff);
        staffService.insert(staff);
        verify(staffDAO,times(2)).insert(staff);
    }



    @Test
    void findStaffPaginated() {
        staffService.findStaffPaginated(1);
        verify(staffDAO,times(1)).findPaginatedOrderById(0);
        staffService.findStaffPaginated(2);
        verify(staffDAO,times(1)).findPaginatedOrderById(5);

    }
}