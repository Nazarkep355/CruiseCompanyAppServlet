package com.example.cruisecompanyappservlet.entity;

public class Staff {
    private long id;
    private String name;
    private String position;

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
