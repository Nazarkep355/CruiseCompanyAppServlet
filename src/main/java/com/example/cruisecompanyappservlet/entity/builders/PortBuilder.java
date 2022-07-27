package com.example.cruisecompanyappservlet.entity.builders;

import com.example.cruisecompanyappservlet.entity.Port;

public class PortBuilder {
    private long id;
    private String city;
    public PortBuilder id(long id){
        this.id=id;
        return this;
    }
    public PortBuilder city(String city){
        this.city = city;
        return this;
    }
    public Port build(){
        Port port =new Port();
        port.setCity(city);
        port.setId(id);
        return port;
    }
}
