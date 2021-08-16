package com.are.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemDetails implements Serializable {
    public int equipmentId;
    public int equipmentType;
    public String brand = "";
    public String model = "";

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

    public double getFunctional() {
        return functional;
    }

    public void setFunctional(float functional) {
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


    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isEnquired() {
        return isEnquired;
    }

    public void setEnquired(boolean enquired) {
        isEnquired = enquired;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String year;
    public float functional;
    public String functionalType = "";
    public int quantity;
    public float unitPrice;
    public String description = "";

    public void setYear(String year) {
        this.year = year;
    }

    public String location = "";
    public boolean isActive;
    public String name = "";
    public int itemType;
    public String createdOn = "";
    public boolean isEnquired;
    public String companyName = "";
    public int companyId;
    public String email = "";
    public String contact = "";
    public List<Images> images = new ArrayList<>();
    public Video video = new Video();

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public ItemEnquiry getItemEnquiry() {
        return itemEnquiry;
    }

    public void setItemEnquiry(ItemEnquiry itemEnquiry) {
        this.itemEnquiry = itemEnquiry;
    }

    public ItemEnquiry itemEnquiry = new ItemEnquiry();


    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public class Images {
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

    public class Video {
        public String url;
        public int videoType;
        public int referenceid;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getVideoType() {
            return videoType;
        }

        public void setVideoType(int videoType) {
            this.videoType = videoType;
        }

        public int getReferenceid() {
            return referenceid;
        }

        public void setReferenceid(int referenceid) {
            this.referenceid = referenceid;
        }
    }

    public class ItemEnquiry {
        public int itemEnquiryId;
        public double desiredPrice;
        public String enquiryDate="";
        public String remarks="";

        public int getItemEnquiryId() {
            return itemEnquiryId;
        }

        public void setItemEnquiryId(int itemEnquiryId) {
            this.itemEnquiryId = itemEnquiryId;
        }

        public double getDesiredPrice() {
            return desiredPrice;
        }

        public void setDesiredPrice(double desiredPrice) {
            this.desiredPrice = desiredPrice;
        }

        public String getEnquiryDate() {
            return enquiryDate;
        }

        public void setEnquiryDate(String enquiryDate) {
            this.enquiryDate = enquiryDate;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}

