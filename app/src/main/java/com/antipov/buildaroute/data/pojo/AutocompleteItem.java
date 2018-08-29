package com.antipov.buildaroute.data.pojo;

import com.google.gson.annotations.SerializedName;

public class AutocompleteItem {
    @SerializedName("formatted_address")
    private String formattedAddress;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
