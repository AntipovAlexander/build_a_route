package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.data.pojo.WayPoint;
import com.antipov.buildaroute.ui.base.IBaseView;

public interface MapView extends IBaseView {
    void showAddressDialog(int request);

    void addMarker(float lat, float lng);

    int getWaypointsCount();

    void insertWayPoint(WayPoint item);

    void notifyWaypointsLimit();
}
