package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.StaffDAO;
import com.example.cruisecompanyappservlet.entity.Staff;

import java.util.List;

public class StaffService {
    private StaffDAO staffDAO;
    public StaffService(){
        staffDAO = new StaffDAO();
    }
    public Staff findById(long id){
        return staffDAO.findById(id);
    }
    public List<Staff> findStaffPaginated(int page){
        int offset = (page*5)-5;
        return staffDAO.findPaginatedOrderById(offset);
    }
}
