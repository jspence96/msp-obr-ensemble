package com.chacetech.serviceproviders.service;

import com.chacetech.serviceproviders.common.dao.ServiceProviderRepository;
import com.chacetech.serviceproviders.common.exception.ServiceProviderException;
import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderCreateRequest;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderResponse;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderUpdateRequest;
import com.chacetech.serviceproviders.controller.ServiceProviderControllerV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ServiceProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProviderService.class);

    private static final String MSP_NAME_IS_REQUIRED = "mspName is required";
    @Autowired
    ServiceProviderRepository serviceProviderRepository;

    public List<ManagedServiceProvider> getAllServiceProviders() throws ServiceProviderException  {
        try {
            return serviceProviderRepository.findAll();
        } catch (Exception e) {
            throw new ServiceProviderException("Error retrieving all service providers", e);
        }
    }

    public ManagedServiceProvider getServiceProviderByName(String mspName) throws ServiceProviderException  {
        if (StringUtils.isEmpty(mspName)) {
            throw new ServiceProviderException(MSP_NAME_IS_REQUIRED);
        }
        try {
            return serviceProviderRepository.findByName(mspName).get(0);
        } catch (Exception e) {
            throw new ServiceProviderException("Error retrieving service srovider by name: " + mspName, e);
        }
    }

    public void createManagedServiceProvider(
            ManagedServiceProviderCreateRequest managedServiceProviderCreateRequest) throws ServiceProviderException {

        if (managedServiceProviderCreateRequest == null || StringUtils.isEmpty(managedServiceProviderCreateRequest.getMspName())) {
            throw new ServiceProviderException(MSP_NAME_IS_REQUIRED);
        }

        LOGGER.info("entered createManagedServiceProvider - mspName: {}",
                managedServiceProviderCreateRequest.getMspName());

        List<ManagedServiceProvider> managedServiceProviders =
                serviceProviderRepository.findByName(managedServiceProviderCreateRequest.getMspName().toUpperCase());

        if (managedServiceProviders.isEmpty()) {
            serviceProviderRepository.create(managedServiceProviderCreateRequest);
        } else {
            LOGGER.error("creation of service provider failed - mspName: ",
                    managedServiceProviderCreateRequest.getMspName() + " already exists");
            throw new ServiceProviderException("creation of service provider failed - mspName: " +
                    managedServiceProviderCreateRequest.getMspName() + " already exists");

        }
    }

    public void updateManagedServiceProvider(ManagedServiceProviderUpdateRequest managedServiceProviderUpdateRequest) throws ServiceProviderException {

        if (managedServiceProviderUpdateRequest == null || StringUtils.isEmpty(managedServiceProviderUpdateRequest.getMspName())) {
            throw new ServiceProviderException(MSP_NAME_IS_REQUIRED);
        }

        LOGGER.info("entered updateManagedServiceProvider - mspName: {}",
                managedServiceProviderUpdateRequest.getMspName());

        List<ManagedServiceProvider> managedServiceProviders =
                serviceProviderRepository.findByName(managedServiceProviderUpdateRequest.getMspName().toUpperCase());

        if (!managedServiceProviders.isEmpty()) {
            serviceProviderRepository.update(managedServiceProviders.get(0),
                    managedServiceProviderUpdateRequest);
        } else {
            LOGGER.error("update of service provider failed - mspName: ",
                    managedServiceProviderUpdateRequest.getMspName() + " does not exist");
            throw new ServiceProviderException("update of service provider failed - mspName: " +
                    managedServiceProviderUpdateRequest.getMspName() + " does not exist");
        }
    }
}
