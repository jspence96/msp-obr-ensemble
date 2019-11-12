package com.chacetech.serviceproviders.common.model;

import java.io.Serializable;

public class ManagedServiceProviderCreateRequest implements Serializable {

    private static final long serialVersionUID = 22590641985997656L;

    private String mspName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String contactPerson;
    private String contactPhone;

    public String getMspName() {
        return mspName;
    }

    public void setMspName(String mspName) {
        this.mspName = mspName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
