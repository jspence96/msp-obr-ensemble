package com.chacetech.serviceproviders.controller;

import com.chacetech.serviceproviders.common.dao.ServiceProviderRepository;
import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class ServiceProviderControllerV1 {

    private final ServiceProviderRepository serviceProviderRepository;

    @Autowired
    ServiceProviderControllerV1(ServiceProviderRepository serviceProviderRepository) {
        this.serviceProviderRepository = serviceProviderRepository;
    }

    @RequestMapping(value="getManagedServiceProvidersByName", method = RequestMethod.GET)
    public ResponseEntity<List<ManagedServiceProvider>> getManagedServiceProvidersByName(
            @RequestParam(name = "mspName", required = true) String mspName) {

        try {
            return new ResponseEntity<>(serviceProviderRepository.findByName(mspName), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}