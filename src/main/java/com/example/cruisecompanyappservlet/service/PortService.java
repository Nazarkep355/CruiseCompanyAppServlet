package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.PortDAO;
import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.Route;

import java.util.List;
import java.util.Optional;

public class PortService {
    PortDAO portDAO;
    public PortService(){
        portDAO= new PortDAO();
    }
    public PortService(PortDAO portDAO){
        this.portDAO= new PortDAO();
    }
    public List<Port> findPortsPaginated(int page){
        int offset = (5*page)-5;
        return portDAO.findPortOrderByCityPaginated(offset);
    }
    public boolean isPortExists(String city){
        Optional<Port> portOptional = portDAO.findByCity(city);
        return portOptional.isPresent();
    }
    public Port findPortByCity(String city){
        return portDAO.findByCity(city).get();
    }
    public boolean insert(Port port){
        return portDAO.insert(port);
    }
}
