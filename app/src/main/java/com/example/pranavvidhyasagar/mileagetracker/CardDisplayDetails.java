package com.example.pranavvidhyasagar.mileagetracker;

public class CardDisplayDetails {
    String manufacturer,model,regnum;

    public CardDisplayDetails(String manufacturer, String model, String regnum) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.regnum = regnum;
    }
    public CardDisplayDetails() {
        this.manufacturer = "";
        this.model = "";
        this.regnum = "";
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

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }
}
