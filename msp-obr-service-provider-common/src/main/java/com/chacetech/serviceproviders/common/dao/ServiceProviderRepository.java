package com.chacetech.serviceproviders.common.dao;

import com.chacetech.serviceproviders.common.exception.ServiceProviderException;
import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderCreateRequest;
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

    public  List<ManagedServiceProvider> findAll() {
        Query query = new Query();
        Sort sort = new Sort(Sort.Direction.ASC, "mspName");
        query.with(sort);

        List<ManagedServiceProvider> managedServiceProviders =
                mongoTemplate.find(query, ManagedServiceProvider.class, MANAGED_SERVICE_PROVIDERS_COLLECTION);

        return managedServiceProviders;
    }

    public  List<ManagedServiceProvider> findByName(String mspName) {
        Query query = new Query().addCriteria(Criteria.where("mspName").is(mspName));

        Sort sort = new Sort(Sort.Direction.ASC, "mspName");
        query.with(sort);

        List<ManagedServiceProvider> managedServiceProviders =
                mongoTemplate.find(query, ManagedServiceProvider.class, MANAGED_SERVICE_PROVIDERS_COLLECTION);

        return managedServiceProviders;
    }

    public void createManagedServiceProvider(ManagedServiceProviderCreateRequest managedServiceProviderCreateRequest) {
        ManagedServiceProvider managedServiceProvider = createManagedServiceProviderData(managedServiceProviderCreateRequest);
        mongoTemplate.insert(managedServiceProvider, MANAGED_SERVICE_PROVIDERS_COLLECTION);
    }

    private ManagedServiceProvider createManagedServiceProviderData(ManagedServiceProviderCreateRequest managedServiceProviderCreateRequest) {
        ManagedServiceProvider managedServiceProvider = new ManagedServiceProvider();
        managedServiceProvider.setMspName(managedServiceProviderCreateRequest.getMspName().toUpperCase());

        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getAddress())) {
            managedServiceProvider.setAddress(managedServiceProviderCreateRequest.getAddress());
        } else {
            managedServiceProvider.setAddress("");
        }

        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getCity())) {
            managedServiceProvider.setCity((managedServiceProviderCreateRequest.getCity()));
        } else {
            managedServiceProvider.setCity("");
        }

        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getState())) {
            managedServiceProvider.setState(managedServiceProviderCreateRequest.getState());
        } else {
            managedServiceProvider.setState("");
        }

        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getZipCode())) {
            managedServiceProvider.setZipCode(managedServiceProviderCreateRequest.getZipCode());
        } else {
            managedServiceProvider.setZipCode("");
        }

        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getContactPerson())) {
            managedServiceProvider.setContactPerson(managedServiceProviderCreateRequest.getContactPerson());
        } else {
            managedServiceProvider.setContactPerson("");
        }

        if (!StringUtils.isEmpty(managedServiceProviderCreateRequest.getContactPhone())) {
            managedServiceProvider.setContactPhone(managedServiceProviderCreateRequest.getContactPhone());
        } else {
            managedServiceProvider.setContactPhone("");
        }

        return managedServiceProvider;



    }


}
