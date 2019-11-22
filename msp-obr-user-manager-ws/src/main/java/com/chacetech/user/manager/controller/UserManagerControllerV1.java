package com.chacetech.user.manager.controller;

import com.chacetech.serviceproviders.common.dao.ServiceProviderRepository;
import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderCreateRequest;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderResponse;
import com.chacetech.serviceproviders.common.model.ManagedServiceProviderUpdateRequest;
import com.chacetech.users.dao.UserRepository;
import com.chacetech.users.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserManagerControllerV1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerControllerV1.class);

    private static final String FAILURE = "FAILURE";
    private static final String SUCCESS = "SUCCESS";

    private final UserRepository userRepository;

    @Autowired
    UserManagerControllerV1(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value="getAllUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {

        try {
            return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("ERROR: " + e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", e.getMessage());
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="getAllMspUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllMspUsers(
            @RequestParam(name = "mspName", required = true) String mspName) {

        if (StringUtils.isEmpty(mspName)) {
            LOGGER.warn("mspName must be supplied");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", "mspName must be supplied");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(userRepository.findAllMspUsers(mspName), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("ERROR: " + e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", e.getMessage());
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

//    @RequestMapping(value="getUserByUserName", method = RequestMethod.GET)
////    public ResponseEntity<List<ManagedServiceProvider>> getManagedServiceProvidersByName(
////            @RequestParam(name = "mspName", required = true) String mspName,
////            @RequestParam(name = "userName", required = true) String userName) {
////
////        if (StringUtils.isEmpty(mspName) || StringUtils.isEmpty(userName)) {
////            LOGGER.warn("Both mspName and userName must be supplied");
////            HttpHeaders responseHeaders = new HttpHeaders();
////            responseHeaders.set("ERROR", "Both mspName and userName must be supplied");
////            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
////        }
////        try {
////            return new ResponseEntity<>(userRepository.findByUserName(mspName, userName), HttpStatus.OK);
////        } catch (Exception e) {
////            LOGGER.warn("ERROR: " + e.getMessage());
////            HttpHeaders responseHeaders = new HttpHeaders();
////            responseHeaders.set("ERROR", e.getMessage());
////            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
////        }
////    }

//    @RequestMapping(method = RequestMethod.POST,
////            path = { "createManagedServiceProvider" },
////            consumes = { MediaType.APPLICATION_JSON },
////            produces = { MediaType.APPLICATION_JSON }
////    )
////    public ResponseEntity<ManagedServiceProviderResponse> createManagedServiceProvider(
////            @RequestBody ManagedServiceProviderCreateRequest managedServiceProviderCreateRequest) {
////
////        if (managedServiceProviderCreateRequest == null || StringUtils.isEmpty(managedServiceProviderCreateRequest.getMspName())) {
////            LOGGER.error("mspName is required and must be supplied");
////            ManagedServiceProviderResponse managedServiceProviderResponse =
////                    new ManagedServiceProviderResponse(FAILURE, "mspName is required and must be supplied");
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
////        }
////
////        LOGGER.info("entered createManagedServiceProvider - mspName: {}",
////                managedServiceProviderCreateRequest.getMspName());
////
////        try {
////            List<ManagedServiceProvider> managedServiceProviders =
////                    serviceProviderRepository.findByName(managedServiceProviderCreateRequest.getMspName().toUpperCase());
////
////            if (managedServiceProviders.isEmpty()) {
////                serviceProviderRepository.create(managedServiceProviderCreateRequest);
////                ManagedServiceProviderResponse managedServiceProviderResponse =
////                        new ManagedServiceProviderResponse(SUCCESS, "createManagedServiceProvider successful");
////                return ResponseEntity.status(HttpStatus.OK).body(managedServiceProviderResponse);
////            } else {
////                LOGGER.error("createManagedServiceProvider failed - mspName: ",
////                        managedServiceProviderCreateRequest.getMspName() + " already exists");
////                ManagedServiceProviderResponse managedServiceProviderResponse =
////                        new ManagedServiceProviderResponse(FAILURE,
////                                "createManagedServiceProvider failed - mspName: " +
////                                        managedServiceProviderCreateRequest.getMspName() + " already exists");
////                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
////            }
////        } catch (Exception e) {
////            LOGGER.error("createManagedServiceProvider failed - mspName: ",
////                    managedServiceProviderCreateRequest.getMspName(), e);
////            ManagedServiceProviderResponse managedServiceProviderResponse =
////                    new ManagedServiceProviderResponse(FAILURE,
////                            "createManagedServiceProvider failed: " + e.getMessage());
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
////        }
////    }
////
////    @RequestMapping(method = RequestMethod.POST,
////            path = { "updateManagedServiceProvider" },
////            consumes = { MediaType.APPLICATION_JSON },
////            produces = { MediaType.APPLICATION_JSON }
////    )
////    public ResponseEntity<ManagedServiceProviderResponse> updateManagedServiceProvider(
////            @RequestBody ManagedServiceProviderUpdateRequest managedServiceProviderUpdateRequest) {
////
////        if (managedServiceProviderUpdateRequest == null || StringUtils.isEmpty(managedServiceProviderUpdateRequest.getMspName())) {
////            LOGGER.error("mspName is required and must be supplied");
////            ManagedServiceProviderResponse managedServiceProviderResponse =
////                    new ManagedServiceProviderResponse(FAILURE, "mspName is required and must be supplied");
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
////        }
////
////        LOGGER.info("entered managedServiceProviderUpdateRequest - mspName: {}",
////                managedServiceProviderUpdateRequest.getMspName());
////
////        try {
////            List<ManagedServiceProvider> managedServiceProviders =
////                    serviceProviderRepository.findByName(managedServiceProviderUpdateRequest.getMspName().toUpperCase());
////
////            if (!managedServiceProviders.isEmpty()) {
////                serviceProviderRepository.update(managedServiceProviders.get(0), managedServiceProviderUpdateRequest);
////                ManagedServiceProviderResponse managedServiceProviderResponse =
////                        new ManagedServiceProviderResponse(SUCCESS, "createManagedServiceProvider successful");
////                return ResponseEntity.status(HttpStatus.OK).body(managedServiceProviderResponse);
////            } else {
////                LOGGER.error("managedServiceProviderUpdateRequest failed - mspName: ",
////                        managedServiceProviderUpdateRequest.getMspName() + " doesn't exists");
////                ManagedServiceProviderResponse managedServiceProviderResponse =
////                        new ManagedServiceProviderResponse(FAILURE,
////                                "managedServiceProviderUpdateRequest failed - mspName: " +
////                                        managedServiceProviderUpdateRequest.getMspName() + " doesn't exists");
////                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
////            }
////        } catch (Exception e) {
////            LOGGER.error("managedServiceProviderUpdateRequest failed - mspName: ",
////                    managedServiceProviderUpdateRequest.getMspName(), e);
////            ManagedServiceProviderResponse managedServiceProviderResponse =
////                    new ManagedServiceProviderResponse(FAILURE,
////                            "managedServiceProviderUpdateRequest failed: " + e.getMessage());
////            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
////        }
////    }
}