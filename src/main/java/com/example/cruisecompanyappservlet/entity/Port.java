package com.example.cruisecompanyappservlet.entity;

public class Port {
    private long id;
    private String city;

    @Override
    public String toString() {
        return "Port{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
