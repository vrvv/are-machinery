package com.are.model.rest_request;

import java.io.Serializable;

public class EditDetailRequest implements Serializable {
    public String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int userId;
    public String email = "";
}
