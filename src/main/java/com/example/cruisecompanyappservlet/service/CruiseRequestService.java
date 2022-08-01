package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.CruiseRequestDAO;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;
import com.example.cruisecompanyappservlet.entity.Status;
import com.example.cruisecompanyappservlet.entity.User;

import java.util.List;

public class CruiseRequestService {
    private CruiseRequestDAO cruiseRequestDAO;

    public CruiseRequestService() {
        cruiseRequestDAO = new CruiseRequestDAO();
    }

    public CruiseRequest findById(long id) {
        return cruiseRequestDAO.findById(id);
    }

    public boolean insert(CruiseRequest cruiseRequest) {
        return cruiseRequestDAO.insert(cruiseRequest);
    }

    public List<CruiseRequest> getPaginatedList(int page) {
        int offset = (page*5)-5;
        return cruiseRequestDAO.findAllOrderByIdDescPaginated(offset);
    }
    public List<CruiseRequest> findByCruiseAndStatusPaginated(Cruise cruise, Status status, int page){
        int offset = (page*5)-5;
        return cruiseRequestDAO.findByCruiseAndStatusPaginated(cruise,status,offset);
    }
    public List<CruiseRequest> findByUserPaginated(User user, int page){
        int offset = (page*5)-5;
        return cruiseRequestDAO.findByUserPaginated(user,offset);
    }
    public List<CruiseRequest> findByCruisePaginated(Cruise cruise, int page){
        int offset = (page*5)-5;
        return  cruiseRequestDAO.findByCruisePaginated(cruise,offset);
    }
}
