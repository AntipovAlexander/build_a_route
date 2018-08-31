package com.antipov.buildaroute.data.pojo.directions;

import com.google.gson.annotations.SerializedName;

public class Route {

    @SerializedName("overview_polyline")
    private Polyline polyline;

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }
}
