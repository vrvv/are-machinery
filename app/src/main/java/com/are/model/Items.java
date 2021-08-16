package com.are.model;

import java.io.Serializable;

public class Items implements Serializable {
    public int equipmentId;
    public int equipmentType;
    public String brand = "";
    public String model = "";
    public String name = "";
    public String companyName = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(int equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getFunctional() {
        return functional;
    }

    public void setFunctional(double functional) {
        this.functional = functional;
    }

    public String getFunctionalType() {
        return functionalType;
    }

    public void setFunctionalType(String functionalType) {
        this.functionalType = functionalType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isEnquired() {
        return isEnquired;
    }

    public void setEnquired(boolean enquired) {
        isEnquired = enquired;
    }

    public boolean isCompanyVerified() {
        return isCompanyVerified;
    }

    public void setCompanyVerified(boolean companyVerified) {
        isCompanyVerified = companyVerified;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemVideo() {
        return itemVideo;
    }

    public void setItemVideo(String itemVideo) {
        this.itemVideo = itemVideo;
    }

    public String year = "";
    public double functional;
    public String functionalType = "";
    public int quantity;
    public double unitPrice;
    public String description = "";
    public String location = "";
    public boolean isActive;
    public boolean isEnquired;
    public boolean isCompanyVerified;
    public String itemImage = "";
    public String itemVideo = "";
}
