package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.StaffDAO;
import com.example.cruisecompanyappservlet.entity.Staff;
import com.example.cruisecompanyappservlet.entity.builders.StaffBuilder;

import java.util.List;

public class StaffService {
    private StaffDAO staffDAO;

    public StaffService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public StaffService(){
        staffDAO = new StaffDAO();
    }
    public Staff findById(long id){
        return staffDAO.findById(id);
    }
    public boolean isStaffWithNameExists(String name){
        return staffDAO.isStaffWithNameExists(name);
    }
    public boolean insert(String name,String position){
        Staff staff = new StaffBuilder()
                .name(name)
                .position(position)
                .build();
        return staffDAO.insert(staff);
    }
    public boolean insert(Staff staff){
        return staffDAO.insert(staff);
    }
    public List<Staff> findStaffPaginated(int page){
        int offset = (page*5)-5;
        return staffDAO.findPaginatedOrderById(offset);
    }
}
