package com.chacetech.users.dao;

import com.chacetech.common.service.SequenceGeneratorService;
import com.chacetech.users.enums.UserType;
import com.chacetech.users.model.AccessLevel;
import com.chacetech.users.model.User;
import com.chacetech.users.model.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class UserRepository {
//        implements  IUserRepository{

    private static final String USER_COLLECTION = "USER";

    private final MongoTemplate mongoTemplate;
    private final MongoConverter mongoConverter;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public UserRepository(MongoTemplate mongoTemplate,
                          MongoConverter mongoConverter,
                          SequenceGeneratorService sequenceGeneratorService) {
        this.mongoTemplate = mongoTemplate;
        this.mongoConverter = mongoConverter;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    public  List<User> findAll() {
        Query query = new Query();
        Sort sort = new Sort(Sort.Direction.ASC, "userName");
        query.with(sort);

        List<User> users = mongoTemplate.find(query, User.class, USER_COLLECTION);

        return users;
    }

    public  List<User> findAllMspUsers(String mspName) {
        Query query = new Query().addCriteria(Criteria.where("mspName").is(mspName));

        Sort sort = new Sort(Sort.Direction.ASC, "userName");
        query.with(sort);

        List<User> users = mongoTemplate.find(query, User.class, USER_COLLECTION);

        return users;
    }

    public User findMspUserByName(String mspName, String userName) {
        Query query = new Query().addCriteria(
                Criteria.where("mspName").is(mspName).and("userName").is(userName));

        Sort sort = new Sort(Sort.Direction.ASC, "userName");
        query.with(sort);

        List<User> users = mongoTemplate.find(query, User.class, USER_COLLECTION);

        return users.get(0);
    }

    public  User findSuperUser(String userName) {
        Query query = new Query().addCriteria(Criteria.where("userName").is(userName)
                        .and("userType").is(UserType.SUPER_USER)).limit(1);

        List<User> users = mongoTemplate.find(query, User.class, USER_COLLECTION);

        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public  User findByMspIdUserNameAndType(String mspId, String userName, String userType) {
        Query query = new Query().addCriteria(Criteria.where("mspId").is(mspId)
                .and("userName").is(userName)
                .and("userType").is(userType)).limit(1);

        List<User> users = mongoTemplate.find(query, User.class, USER_COLLECTION);

        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public void create(UserCreateRequest userCreateRequest) {
        User user = createUser(userCreateRequest);
        mongoTemplate.insert(user, USER_COLLECTION);
    }

    private User createUser(UserCreateRequest userCreateRequest) {
        User user = new User();

        user.setUserId(sequenceGeneratorService.generateSequence(User.USER_SEQUENCE_NAME));
        user.setUserType(userCreateRequest.getUserType());

        if (!StringUtils.isEmpty(userCreateRequest.getMspId())) {
            user.setMspId(userCreateRequest.getMspId());
        } else {
            user.setMspId("");
        }

        user.setUserName(userCreateRequest.getUserName());
        user.setPassword(userCreateRequest.getPassword());
        user.setAccessLevels(userCreateRequest.getAccessLevels());

        return user;
    }

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
