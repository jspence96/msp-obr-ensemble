package com.chacetech.users.model;

import com.chacetech.users.enums.AccessType;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class AccessLevel implements Serializable {

    private static final long serialVersionUID = 3935618317983894159L;

    @Id
    private String id;
    private AccessType accessType;
    private boolean readOnly;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }
}
