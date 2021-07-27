package com.are.model.rest_request;

import java.io.Serializable;

public class AddCompanyRequest implements Serializable {
    public int companyId;
    public String proprietor = "";
    public String pan = "";
    public String panDoc = "";
    public String gstin = "";
    public String gstDoc = "";

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getProprietor() {
        return proprietor;
    }

    public void setProprietor(String proprietor) {
        this.proprietor = proprietor;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPanDoc() {
        return panDoc;
    }

    public void setPanDoc(String panDoc) {
        this.panDoc = panDoc;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getGstDoc() {
        return gstDoc;
    }

    public void setGstDoc(String gstDoc) {
        this.gstDoc = gstDoc;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCinDoc() {
        return cinDoc;
    }

    public void setCinDoc(String cinDoc) {
        this.cinDoc = cinDoc;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String cin = "";
    public String cinDoc = "";
    public int userId;
}
