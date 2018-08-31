package com.antipov.buildaroute.utils.converter;

import com.antipov.buildaroute.data.pojo.autocomplete.Location;
import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;

import java.util.ArrayList;
import java.util.List;

public class ArgsConverter {

    /**
     * converts float lat lng coordinates to string coordinates which appropriates for api calls
     *
     * @param location - location to conver
     * @return - converted location
     */
    public static String convert(Location location){
        return String.format("%s,%s", location.getLat(), location.getLng());
    }

    public static String convertToWaypoint(List<WayPoint> wayPoints) {
        StringBuilder builder = new StringBuilder();
        for (WayPoint wayPoint: wayPoints) {
            builder
                    .append("via:")
                    .append(convert(wayPoint.getGeometry().getLocation()))
                    .append("|");
        }

        return builder.toString();
    }
}
