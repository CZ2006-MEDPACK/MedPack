package com.example.csyvi.medpack;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

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

    /**
     * Instantiates a new Clinic.
     *
     * @param name           the name
     * @param address        the address
     * @param postalCode     the postal code
     * @param phone_number   the phone number
     * @param operating_hour the operating hour
     */
    public Clinic(String name, String address, String postalCode, String phone_number, String operating_hour) {
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone_number = phone_number;
        this.operating_hour = operating_hour;
    }

    /**
     * Sets latitude.
     *
     * @param latitude the latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets longitude.
     *
     * @param longitude the longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhone_number() {
        return phone_number;
    }


    /**
     * Gets operating hour.
     *
     * @return the operating hour
     */
    public String getOperating_hour() {
        return operating_hour;
    }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }
}
