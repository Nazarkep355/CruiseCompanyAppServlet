package com.example.cruisecompanyappservlet.entity.builders;

import com.example.cruisecompanyappservlet.entity.Staff;

public class StaffBuilder {
    private long id;
    private String name;
    private String position;

    public StaffBuilder id(long id) {
        this.id = id;
        return this;
    }

    public StaffBuilder name(String name) {
        this.name = name;
        return this;
    }

    public StaffBuilder position(String position) {
        this.position = position;
        return this;
    }

    public Staff build() {
        Staff staff = new Staff();
        staff.setId(id);
        staff.setName(name);
        staff.setPosition(position);
        return staff;
    }
}
