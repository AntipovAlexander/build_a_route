package com.antipov.buildaroute.data.db.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "routes")
public class Route {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "route")
    private String encodedRoute;

    @Generated(hash = 1547692140)
    public Route(Long id, String encodedRoute) {
        this.id = id;
        this.encodedRoute = encodedRoute;
    }

    @Generated(hash = 467763370)
    public Route() {
    }

    public Route(String encodedRoute) {
        this.encodedRoute = encodedRoute;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncodedRoute() {
        return this.encodedRoute;
    }

    public void setEncodedRoute(String encodedRoute) {
        this.encodedRoute = encodedRoute;
    }

    
}
