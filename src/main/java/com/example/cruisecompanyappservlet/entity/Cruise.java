package com.example.cruisecompanyappservlet.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Cruise {
    private long id;
    private Route route;
    private HashMap<Port, Date> schedule;
    private int cost;
    private List<Staff> staff;
    private HashMap<RoomClass,Integer> freePlaces;
    private Status status;
    private int seats;

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", route=" + route +
                ", schedule=" + schedule +
                ", cost=" + cost +
                ", staff=" + staff +
                ", freePlaces=" + freePlaces +
                ", seats=" + seats +
                ", status=" + status +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public HashMap<Port, Date> getSchedule() {
        return schedule;
    }

    public void setSchedule(HashMap<Port, Date> schedule) {
        this.schedule = schedule;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public HashMap<RoomClass, Integer> getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(HashMap<RoomClass, Integer> freePlaces) {
        this.freePlaces = freePlaces;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
    public Date departureDate(){
        return schedule.get(route.getPorts().get(0));
    }
    public int daysInJourney(){
        return route.getDelays().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
