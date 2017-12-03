package com.project.pro112.hydrateam.thepolycoffee.models;

import java.io.Serializable;

/**
 * Created by Huyn_TQT on 12/3/2017.
 */

public class AddressLocation implements Serializable {
    private String id, address;
    private double latitude, longitude;

    public AddressLocation(String address, double latitude, double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddressLocation(String id, String address, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public AddressLocation() {
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
