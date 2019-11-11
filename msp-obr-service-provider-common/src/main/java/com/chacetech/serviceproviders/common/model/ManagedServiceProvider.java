package com.chacetech.serviceproviders.common.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "MANAGED_SERVICE_PROVIDERS")
public class ManagedServiceProvider {

    @Id
    private String id;
    private String mspName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMspName() {
        return mspName;
    }

    public void setMspName(String mspName) {
        this.mspName = mspName;
    }
}
