package com.chacetech.serviceproviders.common.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "MANAGED_SERVICE_PROVIDERS")
public class ManagedServiceProvider implements Serializable {

    private static final long serialVersionUID = -6498095929493541232L;

    @Transient
    public static final String SEQUENCE_NAME = "managed_service_provider_sequence";

    @Id
    private String id;
    private long mspId;
    private String mspName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String contactPerson;
    private String contactPhone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getMspId() {
        return mspId;
    }

    public void setMspId(long mspId) {
        this.mspId = mspId;
    }

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
