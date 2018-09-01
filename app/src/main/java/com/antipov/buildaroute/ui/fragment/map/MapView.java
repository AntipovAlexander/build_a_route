package com.antipov.buildaroute.ui.fragment.map;

import com.antipov.buildaroute.data.pojo.autocomplete.WayPoint;
import com.antipov.buildaroute.ui.base.IBaseView;

import java.util.List;

public interface MapView extends IBaseView {
    void showAddressDialog(int request);

    void addMarker(float lat, float lng, int requestCode);

    int getWaypointsCount();

    void insertWayPoint(WayPoint item);

    void notifyWaypointsLimit();

    WayPoint getStartPoint();

    WayPoint getFinishPoint();

    void notifyNullStart();

    void notifyNullFinish();

    List<WayPoint> getWaypoints();

    void updateStartText(String formattedAddress);

    void updateFinishText(String formattedAddress);

    void removeOldPolyline();

    void createNewPolyline(String coordinates);

    void removeOldFinish();

    void removeOldStart();

    void animateDriving(Long t);

    int getRouteLength();

    void onFinishReached();
}
