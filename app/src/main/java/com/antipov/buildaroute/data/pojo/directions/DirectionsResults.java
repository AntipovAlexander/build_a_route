package com.antipov.buildaroute.data.pojo.directions;

import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;

import java.util.Collections;
import java.util.List;

public class DirectionsResults {

    private List<Route> routes;
    private String status;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getRoutes().get(0).getPolyline().getPolyline();
    }

    public boolean isSuccess() {
        return status.toLowerCase().equals("ok");
    }

    public static DirectionsResults getForTests(String status) {
        DirectionsResults dr = new DirectionsResults();
        dr.setStatus(status);

        Route r = new Route();
        Polyline p = new Polyline();
        p.setPolyline("mwxfHs}{tEYn@yCxG{EfL}@rBw@aAk@s@qAuAYQa@KiGeBnAiMv@iIlAuMDi@La@TaBLkBCy@KyAIe@Ym@SAMAKAQB_@Pk@nAWb@UTSH_@H]SkBkA{IwFi[}R{XyPkNcI{BgAqG{C?g@BUJWPGVFp@^dBx@vB`ARFNDZ?P?RGHM^yA~@}Ff@kCwAy@}BmAc@WKd@CHC@E@");
        r.setPolyline(p);
        dr.setRoutes(Collections.singletonList(r));
        return dr;
    }
}
