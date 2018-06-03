package com.example.pranavvidhyasagar.mileagetracker;

/**
 * Created by h283245 on 6/3/18.
 */

public class VehicleCard {
    String email, manufacturer, model, type;


    public VehicleCard(String email, String manufacturer, String model, String type) {
        this.email = email;
        this.manufacturer = manufacturer;
        this.model = model;
        this.type = type;
    }


    public VehicleCard() {
        this.email = "";
        this.manufacturer = "";
        this.model = "";
        this.type = "";
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
}