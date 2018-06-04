package com.example.pranavvidhyasagar.mileagetracker;

/**
 * Created by h283245 on 6/3/18.
 */

public class VehicleCard {
    String email, manufacturer, model, type;
    float mileage, distance;


    public VehicleCard(String email, String manufacturer, String model, String type,float mileage,float distance) {
        this.email = email;
        this.manufacturer = manufacturer;
        this.model = model;
        this.type = type;
        this.mileage = mileage;
        this.distance = distance;
    }
    public VehicleCard() {
        this.email = "";
        this.manufacturer = "";
        this.model = "";
        this.type = "";
        this.mileage = 0;
        this.distance = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public float getMileage() {
        return mileage;
    }

    public void setMileage(float mileage) {
        this.mileage = mileage;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}