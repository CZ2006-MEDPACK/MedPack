package com.example.csyvi.medpack;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

/**
 * The type Clinic.
 */
public class Clinic {
    private String name;
    private String address;
    private String postalCode;
    private String phone_number;
    private String operating_hour;
    private double longitude;
    private double latitude;
    private double distance;


    public Clinic(String name, String address, String postalCode, String phone_number, String operating_hour, double longitude, double latitude, double distance) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone_number = phone_number;
        this.operating_hour = operating_hour;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
    }

    public Clinic(String name, String address, String postalCode, String phone_number, String operating_hour, double longitude, double latitude) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone_number = phone_number;
        this.operating_hour = operating_hour;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Clinic (String name, String postalCode)
    {
        this.name = name;
        this.postalCode = postalCode;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getOperating_hour() {
        return operating_hour;
    }

    public double getDistance() {
        return distance;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setOperating_hour(String operating_hour) {
        this.operating_hour = operating_hour;
    }

    @Override
    public String toString() {
        return "Clinic{" + name +
                "~" + address  +
                "~" + postalCode +
                "~" + phone_number +
                "~" + operating_hour +
                "~" + longitude +
                "~" + latitude +
                "~" + distance +
                '}';
    }

}
