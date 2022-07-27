package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.CruiseDAO;
import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.RoomClass;
import com.example.cruisecompanyappservlet.entity.builders.PortBuilder;
import com.example.cruisecompanyappservlet.util.CruiseUtil;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println(Date.from(Instant.now()).getTime());
        CruiseDAO cruiseDAO = new CruiseDAO();
        List<Cruise> cruises = cruiseDAO.findAll();
        Cruise cruise =cruises.get(0);
        System.out.println(cruise);
        cruise.getFreePlaces().put(RoomClass.PREMIUM,cruise.getFreePlaces().get(RoomClass.PREMIUM)-1);
        cruiseDAO.update(cruise);
        cruises = cruiseDAO.findAll();
        System.out.println(cruises.get(0));
    }
}
