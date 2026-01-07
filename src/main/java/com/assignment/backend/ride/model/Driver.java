package com.assignment.backend.ride.model;


/**
 * Represents a driver with location and availability.
 */
public class Driver {

    private String id;
    private double lat;
    private double lon;
    private boolean available = true;

    public Driver(String id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() { return id; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
