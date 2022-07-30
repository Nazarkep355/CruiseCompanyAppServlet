package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.CruiseDAO;
import com.example.cruisecompanyappservlet.entity.Cruise;

import java.util.List;

public class CruiseService {
    private CruiseDAO cruiseDAO;
    public CruiseService(CruiseDAO cruiseDAO){
        this.cruiseDAO = cruiseDAO;
    }
    public CruiseService(){
        cruiseDAO=new CruiseDAO();
    }
    public Cruise findById(long id){
        return cruiseDAO.findById(id);
    }
    public List<Cruise> getAllCruises(){
        return cruiseDAO.findAll();
    }
    public List<Cruise> getActualCruisesPaginated(int page){
        int offset= (page*5)-5;
        return cruiseDAO.findActualPaginatedOrderByIdDesc(offset);
    }
    public List<Cruise> getAllActualCruisesWithFreePlacesPaginated(int page){
        int offset= (page*5)-5;
        return cruiseDAO.findActualWithFreePlacesOrderByIdPaginated(offset);
    }
    public List<Cruise> getCruisesByCity(String city, int page){
        int offset=(page*5)-5;
        return cruiseDAO.findActualWithFreePlacesCityLike(city,offset);
    }
    public List<Cruise> getPaginated(int page){
        int offset=(page*5)-5;
        return cruiseDAO.findAllPaginatedOrderByIdDesc(offset);
    }
    public boolean insert(Cruise cruise){
        return cruiseDAO.insert(cruise);
    }
    public boolean update(Cruise cruise){
        return cruiseDAO.update(cruise);
    }
}
