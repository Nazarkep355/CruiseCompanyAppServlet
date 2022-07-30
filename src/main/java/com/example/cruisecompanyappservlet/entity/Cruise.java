package com.example.cruisecompanyappservlet.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Cruise {
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

    @Override
    public String toString() {
        return "Cruise{" +
                "id=" + id +
                ", route=" + route +
                ", schedule=" + schedule +
                ", costEconom=" + costEconom +
                ", costMiddle=" + costMiddle +
                ", costPremium=" + costPremium +
                ", staff=" + staff +
                ", freePlaces=" + freePlaces +
                ", status=" + status +
                ", seats=" + seats +
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

    public int getCostEconom() {
        return costEconom;
    }

    public void setCostEconom(int costEconom) {
        this.costEconom = costEconom;
    }

    public int getCostMiddle() {
        return costMiddle;
    }

    public void setCostMiddle(int costMiddle) {
        this.costMiddle = costMiddle;
    }

    public int getCostPremium() {
        return costPremium;
    }

    public void setCostPremium(int costPremium) {
        this.costPremium = costPremium;
    }
}
