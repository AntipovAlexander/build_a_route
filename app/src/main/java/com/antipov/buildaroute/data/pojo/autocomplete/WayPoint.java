package com.antipov.buildaroute.data.pojo.autocomplete;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WayPoint implements Parcelable {
    @SerializedName("formatted_address")
    private String formattedAddress;

    private Geometry geometry;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.formattedAddress);
        dest.writeParcelable(this.geometry, flags);
    }

    public WayPoint() {
    }

    protected WayPoint(Parcel in) {
        this.formattedAddress = in.readString();
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
    }

    public static final Parcelable.Creator<WayPoint> CREATOR = new Parcelable.Creator<WayPoint>() {
        @Override
        public WayPoint createFromParcel(Parcel source) {
            return new WayPoint(source);
        }

        @Override
        public WayPoint[] newArray(int size) {
            return new WayPoint[size];
        }
    };

    static public WayPoint getForTests() {
        WayPoint wayPoint = new WayPoint();
        Geometry geometry = new Geometry();
        geometry.setLocation(new Location());
        wayPoint.setGeometry(geometry);
        return wayPoint;
    }

    @Override
    public String toString() {
        return getFormattedAddress();
    }
}
