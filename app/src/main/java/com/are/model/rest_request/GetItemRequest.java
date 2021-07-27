package com.are.model.rest_request;

import java.io.Serializable;

public class GetItemRequest implements Serializable {
    public int fromCount;
    public int toCount;
    public String itemType = "";

    public int getFromCount() {
        return fromCount;
    }

    public void setFromCount(int fromCount) {
        this.fromCount = fromCount;
    }

    public int getToCount() {
        return toCount;
    }

    public void setToCount(int toCount) {
        this.toCount = toCount;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String brand = "";
    public String location = "";
    public int userid;
}
