package com.techhive.smartdrive.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by naman on 29/03/18.
 */

public class LocationDirections {

    private String name;
    private String address;
    private LatLng latLng;

    public LocationDirections(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
