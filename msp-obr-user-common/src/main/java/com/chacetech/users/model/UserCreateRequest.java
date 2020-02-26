package com.chacetech.users.model;

import com.chacetech.users.enums.UserType;

import java.io.Serializable;
import java.util.List;

public class UserCreateRequest implements Serializable {

    private static final long serialVersionUID = 22590641985997656L;

    private UserType userType;
    private String mspId;
    private String userName;
    private String password;
    private List<AccessLevel> accessLevels;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getMspId() {
        return mspId;
    }

    public void setMspId(String mspId) {
        this.mspId = mspId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AccessLevel> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(List<AccessLevel> accessLevels) {
        this.accessLevels = accessLevels;
    }


}
