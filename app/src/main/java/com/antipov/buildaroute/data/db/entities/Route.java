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

    @Property(nameInDb = "created_at")
    private Long createdAt;

    @Generated(hash = 1912364383)
    public Route(Long id, String encodedRoute, Long createdAt) {
        this.id = id;
        this.encodedRoute = encodedRoute;
        this.createdAt = createdAt;
    }

    @Generated(hash = 467763370)
    public Route() {
    }

    public Route(String encodedRoute, long time) {
        this.encodedRoute = encodedRoute;
        this.createdAt = time;
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

    public Long getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    
}
