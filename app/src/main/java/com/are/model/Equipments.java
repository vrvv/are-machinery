package com.are.model;

import java.io.Serializable;

public class Equipments implements Serializable {
    public String name = "";
    public int itemType;

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
}
