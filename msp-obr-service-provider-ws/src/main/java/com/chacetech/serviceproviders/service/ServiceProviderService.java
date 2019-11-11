package com.chacetech.serviceproviders.service;

import com.chacetech.serviceproviders.common.dao.ServiceProviderRepository;
import com.chacetech.serviceproviders.common.exception.ServiceProviderException;
import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceProviderService {

    @Autowired
    ServiceProviderRepository serviceProviderRepository;

    public List<ManagedServiceProvider> getAllServiceProvidersByName(String name) throws ServiceProviderException  {
        try {
            return serviceProviderRepository.findByName(name);
        } catch (Exception e) {
            throw new ServiceProviderException("Error retrieving Service Provider by name: " + name, e);
        }
    }
}
