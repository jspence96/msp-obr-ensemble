package com.chacetech.user.manager.controller;

import com.chacetech.user.manager.exception.UserManagerException;
import com.chacetech.user.manager.service.UserManagerService;
import com.chacetech.users.enums.UserType;
import com.chacetech.users.model.User;
import com.chacetech.users.model.UserCreateRequest;
import com.chacetech.users.model.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/v1")
public class UserManagerControllerV1 {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerControllerV1.class);

    private static final String FAILURE = "FAILURE";
    private static final String SUCCESS = "SUCCESS";

    private static final String USERNAME_PATTERN = "^[a-z0-9_-]{8,15}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private Pattern userNamePattern;
    private Pattern passwordPattern;

    private final UserManagerService userManagerService;

    @Autowired
    UserManagerControllerV1(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
        userNamePattern = Pattern.compile(USERNAME_PATTERN);
        passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    }

    @RequestMapping(value="getAllUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {

        try {
            userManagerService.getAllUsers();
            return new ResponseEntity<>(userManagerService.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("ERROR: " + e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", e.getMessage());
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="getAllMspUsers", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllMspUsers(@RequestParam(name = "mspName", required = true) String mspName) {

        if (StringUtils.isEmpty(mspName)) {
            LOGGER.warn("mspName must be supplied");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", "mspName must be supplied");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<>(userManagerService.getAllMspUsers(mspName), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("ERROR: " + e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", e.getMessage());
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="getMspUserByName", method = RequestMethod.GET)
    public ResponseEntity<User> getMspUserByName(
            @RequestParam(name = "mspName", required = true) String mspName,
            @RequestParam(name = "userName", required = true) String userName) {

        if (StringUtils.isEmpty(mspName) || StringUtils.isEmpty(userName)) {
            LOGGER.warn("Both mspName and userName must be supplied");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", "Both mspName and userName must be supplied");
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
        try {
            return new ResponseEntity<>(userManagerService.getMspUserByName(mspName, userName), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("ERROR: " + e.getMessage());
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("ERROR", e.getMessage());
            return new ResponseEntity<>(null, responseHeaders, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST,
            path = { "createUser" },
            consumes = { MediaType.APPLICATION_JSON },
            produces = { MediaType.APPLICATION_JSON }
    )
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {

        try {
            validateCreateRequest(userCreateRequest);
        } catch (UserManagerException ume) {
            LOGGER.error(ume.getMessage());
            UserResponse userResponse = new UserResponse(FAILURE, ume.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
        }

        LOGGER.info("entered createUser - userName: {}", userCreateRequest.getUserName());

        try {
            userManagerService.createUser(userCreateRequest);


            UserResponse userResponse = new UserResponse(SUCCESS, "creation of user successful");
            return ResponseEntity.status(HttpStatus.OK).body(userResponse);

//            } else {
//            List<ManagedServiceProvider> managedServiceProviders =
//                    serviceProviderRepository.findByName(managedServiceProviderCreateRequest.getMspName().toUpperCase());
//
//            if (managedServiceProviders.isEmpty()) {
//                serviceProviderRepository.create(managedServiceProviderCreateRequest);
//                ManagedServiceProviderResponse managedServiceProviderResponse =
//                        new ManagedServiceProviderResponse(SUCCESS, "createManagedServiceProvider successful");
//                return ResponseEntity.status(HttpStatus.OK).body(managedServiceProviderResponse);
//            } else {
//                LOGGER.error("createManagedServiceProvider failed - mspName: ",
//                        managedServiceProviderCreateRequest.getMspName() + " already exists");
//                ManagedServiceProviderResponse managedServiceProviderResponse =
//                        new ManagedServiceProviderResponse(FAILURE,
//                                "createManagedServiceProvider failed - mspName: " +
//                                        managedServiceProviderCreateRequest.getMspName() + " already exists");
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(managedServiceProviderResponse);
//            }
        } catch (Exception e) {
            LOGGER.error("createUser failed - userName: ", userCreateRequest.getUserName(), e);
            UserResponse userResponse = new UserResponse(FAILURE, "createUser failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userResponse);
        }

    }

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

    private void validateCreateRequest(UserCreateRequest userCreateRequest) throws UserManagerException {

        Matcher matcher;

        if (userCreateRequest == null) {
            throw new UserManagerException("userCreateRequest cannot be null");
        }

        if (StringUtils.isEmpty(userCreateRequest.getUserType())) {
            throw new UserManagerException("userType is required and must be supplied");
        }

        if (userCreateRequest.getUserType().equals(UserType.SUPER_USER) && userCreateRequest.getMspId() != null) {
            throw new UserManagerException("userType of SUPER_USER cannot be tied to a managed service provider");
        }

        if (StringUtils.isEmpty(userCreateRequest.getUserName())) {
            throw new UserManagerException("userName is required and must be supplied");
        }

        Matcher userNameMatcher = userNamePattern.matcher(userCreateRequest.getUserName());
        if (!userNameMatcher.matches()) {
            throw new UserManagerException("userName format is not correct - must be 8-15 characters long with a-z, 0-9, underscore, hyphen");
        }

        if (StringUtils.isEmpty(userCreateRequest.getPassword())) {
            throw new UserManagerException("password is required and must be supplied");
        }

        Matcher passwordMatcher = passwordPattern.matcher(userCreateRequest.getPassword());
        if (!passwordMatcher.matches()) {
            throw new UserManagerException("password format is not correct - must be at least 8 characters long with least one uppercase letter, one lowercase letter, one number and one special character");
        }

        if (userCreateRequest.getAccessLevels().isEmpty()) {
            throw new UserManagerException("accessLevels is required and must be supplied");
        }
    }
}