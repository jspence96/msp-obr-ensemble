package com.chacetech.users.model;

import com.chacetech.users.enums.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "USERS")
public class User implements  Serializable {

    private static final long serialVersionUID = 8674595378381933817L;

    @Id
    private String id;
    private UserType userType;
    private String userName;
    private String password;
    private List<AccessLevel> accessLevels;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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