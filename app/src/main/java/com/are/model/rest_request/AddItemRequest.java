package com.are.model.rest_request;

import java.io.Serializable;
import java.util.ArrayList;

public class AddItemRequest implements Serializable {
    public int equipmentType;
    public String brand = "";
    public String model = "";
    public String year = "";
    public String functional = "";
    public String functionalType = "";
    public String quantity = "";
    public String unitPrice = "";
    public String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFunctional() {
        return functional;
    }

    public void setFunctional(String functional) {
        this.functional = functional;
    }

    public String getFunctionalType() {
        return functionalType;
    }

    public void setFunctionalType(String functionalType) {
        this.functionalType = functionalType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ArrayList<Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }

    public ArrayList<Video> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<Video> video) {
        this.video = video;
    }

    public String description = "";
    public String location = "";
    public String itemType = "";
    public String createdBy = "";
    public ArrayList<Images> images = new ArrayList<>();
    public ArrayList<Video> video = new ArrayList<>();

    public static class Images {
        public String url;
        public int positionType;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getPositionType() {
            return positionType;
        }

        public void setPositionType(int positionType) {
            this.positionType = positionType;
        }
    }

    public static class Video {
        public String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
