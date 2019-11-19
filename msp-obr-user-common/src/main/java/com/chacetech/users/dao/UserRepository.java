package com.chacetech.users.dao;

import com.chacetech.users.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class UserRepository implements  IUserRepository{

    private static final String USERS_COLLECTION = "USERS";

    private final MongoTemplate mongoTemplate;
    private final MongoConverter mongoConverter;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate,
                          MongoConverter mongoConverter) {
        this.mongoTemplate = mongoTemplate;
        this.mongoConverter = mongoConverter;
    }

    public  List<User> findAll() {
        Query query = new Query();
        Sort sort = new Sort(Sort.Direction.ASC, "userName");
        query.with(sort);

        List<User> users = mongoTemplate.find(query, User.class, USERS_COLLECTION);

        return users;
    }

    public  List<User> findByUserName(String userName) {
        Query query = new Query().addCriteria(Criteria.where("userName").is(userName));

        Sort sort = new Sort(Sort.Direction.ASC, "userName");
        query.with(sort);

        List<User> users = mongoTemplate.find(query, User.class, USERS_COLLECTION);

        return users;
    }

//    public void create(ManagedServiceProviderCreateRequest managedServiceProviderCreateRequest) {
//        ManagedServiceProvider managedServiceProvider = createManagedServiceProvider(managedServiceProviderCreateRequest);
//        mongoTemplate.insert(managedServiceProvider, MANAGED_SERVICE_PROVIDERS_COLLECTION);
//    }
//
//    private ManagedServiceProvider createManagedServiceProvider(ManagedServiceProviderCreateRequest managedServiceProviderCreateRequest) {
//        ManagedServiceProvider managedServiceProvider = new ManagedServiceProvider();
//        managedServiceProvider.setMspName(managedServiceProviderCreateRequest.getMspName().toUpperCase());
//
//        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getAddress())) {
//            managedServiceProvider.setAddress(managedServiceProviderCreateRequest.getAddress());
//        } else {
//            managedServiceProvider.setAddress("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getCity())) {
//            managedServiceProvider.setCity((managedServiceProviderCreateRequest.getCity()));
//        } else {
//            managedServiceProvider.setCity("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getState())) {
//            managedServiceProvider.setState(managedServiceProviderCreateRequest.getState());
//        } else {
//            managedServiceProvider.setState("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getZipCode())) {
//            managedServiceProvider.setZipCode(managedServiceProviderCreateRequest.getZipCode());
//        } else {
//            managedServiceProvider.setZipCode("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getContactPerson())) {
//            managedServiceProvider.setContactPerson(managedServiceProviderCreateRequest.getContactPerson());
//        } else {
//            managedServiceProvider.setContactPerson("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getContactPhone())) {
//            managedServiceProvider.setContactPhone(managedServiceProviderCreateRequest.getContactPhone());
//        } else {
//            managedServiceProvider.setContactPhone("");
//        }
//
//        return managedServiceProvider;
//    }
//
//    public void update(ManagedServiceProvider managedServiceProvider, ManagedServiceProviderUpdateRequest managedServiceProviderUpdateRequest) {
//        updateManagedServiceProvider(managedServiceProvider, managedServiceProviderUpdateRequest);
//        org.bson.Document document = new org.bson.Document();
//        mongoConverter.write(managedServiceProvider, document);
//        Query query = new Query().addCriteria(Criteria.where("mspName").is(
//                managedServiceProvider.getMspName().toUpperCase()));
//        Update update = Update.fromDocument(document);
//        mongoTemplate.upsert(query, update, MANAGED_SERVICE_PROVIDERS_COLLECTION);
//    }
//
//
//    private void updateManagedServiceProvider(ManagedServiceProvider managedServiceProvider,
//                                              ManagedServiceProviderUpdateRequest managedServiceProviderUpdateRequest) {
//        if (!StringUtils.isEmpty(managedServiceProviderUpdateRequest.getAddress())) {
//            managedServiceProvider.setAddress(managedServiceProviderUpdateRequest.getAddress());
//        } else {
//            managedServiceProvider.setAddress("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderUpdateRequest.getCity())) {
//            managedServiceProvider.setCity((managedServiceProviderUpdateRequest.getCity()));
//        } else {
//            managedServiceProvider.setCity("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderUpdateRequest.getState())) {
//            managedServiceProvider.setState(managedServiceProviderUpdateRequest.getState());
//        } else {
//            managedServiceProvider.setState("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderUpdateRequest.getZipCode())) {
//            managedServiceProvider.setZipCode(managedServiceProviderUpdateRequest.getZipCode());
//        } else {
//            managedServiceProvider.setZipCode("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderUpdateRequest.getContactPerson())) {
//            managedServiceProvider.setContactPerson(managedServiceProviderUpdateRequest.getContactPerson());
//        } else {
//            managedServiceProvider.setContactPerson("");
//        }
//
//        if (!StringUtils.isEmpty(managedServiceProviderUpdateRequest.getContactPhone())) {
//            managedServiceProvider.setContactPhone(managedServiceProviderUpdateRequest.getContactPhone());
//        } else {
//            managedServiceProvider.setContactPhone("");
//        }
//    }
}
