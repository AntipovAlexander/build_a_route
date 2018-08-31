package com.antipov.buildaroute.data.pojo.directions;

import com.google.gson.annotations.SerializedName;

public class Polyline {
    @SerializedName("points")
    private String polyline;

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }
}
