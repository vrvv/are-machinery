package com.are.model;

import java.io.Serializable;

public class MyItemEnquiries implements Serializable {
    public int itemEnquiryId;
    public String enquiryDate = "";

  public int getItemEnquiryId() {
    return itemEnquiryId;
  }

  public void setItemEnquiryId(int itemEnquiryId) {
    this.itemEnquiryId = itemEnquiryId;
  }

  public String getEnquiryDate() {
    return enquiryDate;
  }

  public void setEnquiryDate(String enquiryDate) {
    this.enquiryDate = enquiryDate;
  }

  public double getDesiredPrice() {
    return desiredPrice;
  }

  public void setDesiredPrice(double desiredPrice) {
    this.desiredPrice = desiredPrice;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public double desiredPrice;

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String remarks = "";
    public String name = "";
  public String location = "";
  public String companyName = "";
    public String email = "";
    public String mobile = "";
}
