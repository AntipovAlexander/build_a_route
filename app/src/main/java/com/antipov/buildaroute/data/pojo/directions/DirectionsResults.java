package com.antipov.buildaroute.data.pojo.directions;

import java.util.List;

public class DirectionsResults {

    private List<Route> routes;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return getRoutes().get(0).getPolyline().getPolyline();
    }
}
