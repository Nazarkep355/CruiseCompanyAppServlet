package com.example.cruisecompanyappservlet.entity.builders;

import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.Route;

import java.util.List;

public class RouteBuilder {
    private long id;
    private List<Port> ports;
    private List<Integer> delays;

    public RouteBuilder id(long id) {
        this.id = id;
        return this;
    }

    public RouteBuilder ports(List<Port> ports) {
        this.ports = ports;
        return this;
    }

    public RouteBuilder delays(List<Integer> delays) {
        this.delays = delays;
        return this;
    }

    public Route build() {
        Route route = new Route();
        route.setDelays(delays);
        route.setId(id);
        route.setPorts(ports);
        return route;
    }
}
