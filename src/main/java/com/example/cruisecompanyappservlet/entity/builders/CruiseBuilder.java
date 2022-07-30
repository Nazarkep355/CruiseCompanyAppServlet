package com.example.cruisecompanyappservlet.entity.builders;

import com.example.cruisecompanyappservlet.entity.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CruiseBuilder {
    private long id;
    private Route route;
    private HashMap<Port, Date> schedule;
    private int costEconom;
    private int costMiddle;
    private int costPremium;
    private List<Staff> staff;
    private HashMap<RoomClass,Integer> freePlaces;
    private Status status;
    private int seats;

    public CruiseBuilder id(long id) {
        this.id = id;
        return this;
    }

    public CruiseBuilder route(Route route) {
        this.route = route;
        return this;
    }

    public CruiseBuilder schedule(HashMap<Port, Date> schedule) {
        this.schedule = schedule;
        return this;
    }

    public CruiseBuilder costEconom(int cost) {
        this.costEconom = cost;
        return this;
    }
    public CruiseBuilder costMiddle(int cost) {
        this.costMiddle = cost;
        return this;
    }
    public CruiseBuilder costPremium(int cost) {
        this.costPremium = cost;
        return this;
    }

    public CruiseBuilder staff(List<Staff> staff) {
        this.staff = staff;
        return this;
    }
    public CruiseBuilder freePlaces(HashMap<RoomClass,Integer> freePlaces){
        this.freePlaces =freePlaces;
        return this;
    }
    public CruiseBuilder status(Status status){
        this.status =status;
        return this;
    }
    public CruiseBuilder seats(int seats){
        this.seats= seats;
        return this;
    }
    public Cruise build() {
        Cruise cruise = new Cruise();
        cruise.setId(id);
        cruise.setRoute(route);
        cruise.setSchedule(schedule);
        cruise.setStaff(staff);
        cruise.setCostEconom(costEconom);
        cruise.setCostMiddle(costMiddle);
        cruise.setCostPremium(costPremium);
        cruise.setFreePlaces(freePlaces);
        cruise.setSeats(seats);
        cruise.setStatus(status);
        return cruise;
    }
}
