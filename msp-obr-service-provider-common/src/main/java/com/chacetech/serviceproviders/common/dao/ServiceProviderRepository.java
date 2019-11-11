package com.chacetech.serviceproviders.common.dao;

import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceProviderRepository implements  IServiceProviderRepository{

    private static final String MANAGED_SERVICE_PROVIDERS_COLLECTION = "MANAGED_SERVICE_PROVIDERS";

    private final MongoTemplate mongoTemplate;
    private final MongoConverter mongoConverter;

    @Autowired
    public ServiceProviderRepository(MongoTemplate mongoTemplate,
                                     MongoConverter mongoConverter) {
        this.mongoTemplate = mongoTemplate;
        this.mongoConverter = mongoConverter;
    }

    public  List<ManagedServiceProvider> findByName(String mspName) {
        Query query = new Query().addCriteria(Criteria.where("mspName").is(mspName));

        Sort sort = new Sort(Sort.Direction.ASC, "mspName");
        query.with(sort);

        List<ManagedServiceProvider> managedServiceProviders =
                mongoTemplate.find(query, ManagedServiceProvider.class, MANAGED_SERVICE_PROVIDERS_COLLECTION);

//        List<ManagedServiceProvider> managedServiceProviders =
//                mongoTemplate.findAll(ManagedServiceProvider.class, MANAGED_SERVICE_PROVIDERS_COLLECTION);

        return managedServiceProviders;
    }
}
