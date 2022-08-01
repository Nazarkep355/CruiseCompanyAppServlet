package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.CruiseRequestDAO;
import com.example.cruisecompanyappservlet.entity.CruiseRequest;

public class CruiseRequestService {
    private CruiseRequestDAO cruiseRequestDAO;
    public CruiseRequestService(){
        cruiseRequestDAO = new CruiseRequestDAO();
    }
    public CruiseRequest findById(long id){
        return cruiseRequestDAO.findById(id);
    }
    public boolean insert(CruiseRequest cruiseRequest){
        return cruiseRequestDAO.insert(cruiseRequest);
    }
}
