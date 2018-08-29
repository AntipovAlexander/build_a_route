package com.antipov.buildaroute.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {
    private float lat;
    private float lng;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.lat);
        dest.writeFloat(this.lng);
    }

    public Location() {
    }

    protected Location(Parcel in) {
        this.lat = in.readFloat();
        this.lng = in.readFloat();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
