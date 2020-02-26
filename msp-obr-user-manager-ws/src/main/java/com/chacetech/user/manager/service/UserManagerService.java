package com.chacetech.user.manager.service;

import com.chacetech.user.manager.exception.UserManagerException;
import com.chacetech.users.dao.UserRepository;
import com.chacetech.users.enums.UserType;
import com.chacetech.users.model.User;
import com.chacetech.users.model.UserCreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagerService.class);

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() throws UserManagerException {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new UserManagerException("Error retrieving all users", e);
        }
    }

    public List<User> getAllMspUsers(String mspName) throws UserManagerException {
        try {
            return userRepository.findAllMspUsers(mspName);
        } catch (Exception e) {
            throw new UserManagerException("Error retrieving all users for msp: " + mspName, e);
        }
    }

    public User getMspUserByName(String mspName, String userName) throws UserManagerException {
        try {
            return userRepository.findMspUserByName(mspName, userName);
        } catch (Exception e) {
            throw new UserManagerException("Error retrieving msp user by name - msp: " + mspName + " user: " + userName, e);
        }
    }

    public void createUser(UserCreateRequest userCreateRequest) throws UserManagerException {

        if (userCreateRequest.getUserType().equals(UserType.SUPER_USER)) {
            User superUser = userRepository.findSuperUser(userCreateRequest.getUserName());

            if (superUser == null) {
                userRepository.create(userCreateRequest);
            } else {
                LOGGER.error("creation of user failed - superUser - userName: ",
                        userCreateRequest.getUserName() + " already exists");
                throw new UserManagerException("creation of user failed - superUser - userName: " +
                        userCreateRequest.getUserName() + " already exists");

            }
        } else {
            User user = userRepository.findByMspIdUserNameAndType(userCreateRequest.getMspId(),
                    userCreateRequest.getUserName(), userCreateRequest.getUserType().toString());

            if (user == null) {
                userRepository.create(userCreateRequest);
            } else {
                LOGGER.error("creation of user failed - mspId:" + userCreateRequest.getMspId() +
                        " - userName: " + userCreateRequest.getUserName() + " - userType: " +
                                userCreateRequest.getUserType() + " already exists");
                throw new UserManagerException("creation of user failed - mspId: " + userCreateRequest.getMspId() +
                        " - userName: " + userCreateRequest.getUserName() + " - userType: " +
                                userCreateRequest.getUserType() + " already exists");

            }
        }
    }

//    public void updateManagedServiceProvider(ManagedServiceProviderUpdateRequest managedServiceProviderUpdateRequest) throws ServiceProviderException {
//
//        if (managedServiceProviderUpdateRequest == null || StringUtils.isEmpty(managedServiceProviderUpdateRequest.getMspName())) {
//            throw new ServiceProviderException(MSP_NAME_IS_REQUIRED);
//        }
//
//        LOGGER.info("entered updateManagedServiceProvider - mspName: {}",
//                managedServiceProviderUpdateRequest.getMspName());
//
//        List<ManagedServiceProvider> managedServiceProviders =
//                serviceProviderRepository.findByName(managedServiceProviderUpdateRequest.getMspName().toUpperCase());
//
//        if (!managedServiceProviders.isEmpty()) {
//            serviceProviderRepository.update(managedServiceProviders.get(0),
//                    managedServiceProviderUpdateRequest);
//        } else {
//            LOGGER.error("update of service provider failed - mspName: ",
//                    managedServiceProviderUpdateRequest.getMspName() + " does not exist");
//            throw new ServiceProviderException("update of service provider failed - mspName: " +
//                    managedServiceProviderUpdateRequest.getMspName() + " does not exist");
//        }
//    }
}
