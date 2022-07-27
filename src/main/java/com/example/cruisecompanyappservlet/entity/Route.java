package com.example.cruisecompanyappservlet.entity;

import java.util.List;

public class Route {
    private long id;
    private List<Port> ports;
    private List<Integer> delays;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", ports=" + ports +
                ", delays=" + delays +
                '}';
    }
    public String routeToString(){
        StringBuilder stringBuilder = new StringBuilder();
        for(Port p :ports){
            stringBuilder.append(p.getCity()+" - ");
        }
        stringBuilder.delete(stringBuilder.length()-3,stringBuilder.length());
        return stringBuilder.toString();
    }

    public List<Integer> getDelays() {
        return delays;
    }

    public void setDelays(List<Integer> delays) {
        this.delays = delays;
    }
}
