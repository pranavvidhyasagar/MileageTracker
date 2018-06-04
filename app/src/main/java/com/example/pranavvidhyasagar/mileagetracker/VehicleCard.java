package com.example.pranavvidhyasagar.mileagetracker;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", email);
        result.put("manufacturer", manufacturer);
        result.put("model", model);
        result.put("type", type);
        result.put("mileage", mileage);
        result.put("distance", distance);

        return result;
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