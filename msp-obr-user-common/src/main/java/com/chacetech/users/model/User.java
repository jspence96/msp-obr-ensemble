package com.chacetech.users.model;

import com.chacetech.users.enums.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "USERS")
public class User implements  Serializable {

    private static final long serialVersionUID = 8674595378381933817L;

    @Transient
    public static final String USER_SEQUENCE_NAME = "user_sequence";

    @Id
    private String id;
    private long userId;
    private UserType userType;
    private String mspId;
    private String userName;
    private String password;
    private List<AccessLevel> accessLevels;

    public String getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

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