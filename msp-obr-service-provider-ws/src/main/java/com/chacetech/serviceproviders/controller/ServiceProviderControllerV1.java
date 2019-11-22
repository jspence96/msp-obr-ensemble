package com.chacetech.serviceproviders.controller;

import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderCreateRequest;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderResponse;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderUpdateRequest;
import com.chacetech.serviceproviders.service.ServiceProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ServiceProviderControllerV1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProviderControllerV1.class);

    private static final String FAILURE = "FAILURE";
    private static final String SUCCESS = "SUCCESS";

    private final ServiceProviderService serviceProviderService;

    @Autowired
    ServiceProviderControllerV1(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @RequestMapping(value="getAllManagedServiceProviders", method = RequestMethod.GET)
    public ResponseEntity<List<ManagedServiceProvider>> getAllManagedServiceProviders() {

        try {
            return new ResponseEntity<>(serviceProviderService.getAllServiceProviders(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("ERROR: " + e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", e.getMessage());
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="getManagedServiceProviderByName", method = RequestMethod.GET)
    public ResponseEntity<ManagedServiceProvider> getManagedServiceProvidersByName(
            @RequestParam(name = "mspName", required = false) String mspName) {

        try {
            return new ResponseEntity<>(serviceProviderService.getServiceProviderByName(mspName), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("ERROR: " + e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", e.getMessage());
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            path = { "createManagedServiceProvider" },
            consumes = { MediaType.APPLICATION_JSON },
            produces = { MediaType.APPLICATION_JSON }
    )
    public ResponseEntity<ManagedServiceProviderResponse> createManagedServiceProvider(
            @RequestBody ManagedServiceProviderCreateRequest managedServiceProviderCreateRequest) {

        LOGGER.info("entered createManagedServiceProvider - mspName: {}",
                managedServiceProviderCreateRequest.getMspName());

        try {
            serviceProviderService.createManagedServiceProvider(managedServiceProviderCreateRequest);

                ManagedServiceProviderResponse managedServiceProviderResponse =
                        new ManagedServiceProviderResponse(SUCCESS, "creation of service provider successful");
                return ResponseEntity.status(HttpStatus.OK).body(managedServiceProviderResponse);
        } catch (Exception e) {
            LOGGER.error("createManagedServiceProvider failed - mspName: ",
                    managedServiceProviderCreateRequest.getMspName(), e);
            ManagedServiceProviderResponse managedServiceProviderResponse =
                    new ManagedServiceProviderResponse(FAILURE,
                            e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            path = { "updateManagedServiceProvider" },
            consumes = { MediaType.APPLICATION_JSON },
            produces = { MediaType.APPLICATION_JSON }
    )
    public ResponseEntity<ManagedServiceProviderResponse> updateManagedServiceProvider(
            @RequestBody ManagedServiceProviderUpdateRequest managedServiceProviderUpdateRequest) {

        try {
            serviceProviderService.updateManagedServiceProvider(managedServiceProviderUpdateRequest);

            ManagedServiceProviderResponse managedServiceProviderResponse =
                    new ManagedServiceProviderResponse(SUCCESS, "update of service provider successful");
            return ResponseEntity.status(HttpStatus.OK).body(managedServiceProviderResponse);
        } catch (Exception e) {
            LOGGER.error("createManagedServiceProvider failed - mspName: ",
                    managedServiceProviderUpdateRequest.getMspName(), e);
            ManagedServiceProviderResponse managedServiceProviderResponse =
                    new ManagedServiceProviderResponse(FAILURE,
                            e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
        }
    }
}