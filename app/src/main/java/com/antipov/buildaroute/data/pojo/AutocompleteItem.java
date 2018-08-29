package com.antipov.buildaroute.data.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AutocompleteItem implements Parcelable {
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

    public AutocompleteItem() {
    }

    protected AutocompleteItem(Parcel in) {
        this.formattedAddress = in.readString();
        this.geometry = in.readParcelable(Geometry.class.getClassLoader());
    }

    public static final Parcelable.Creator<AutocompleteItem> CREATOR = new Parcelable.Creator<AutocompleteItem>() {
        @Override
        public AutocompleteItem createFromParcel(Parcel source) {
            return new AutocompleteItem(source);
        }

        @Override
        public AutocompleteItem[] newArray(int size) {
            return new AutocompleteItem[size];
        }
    };
}
