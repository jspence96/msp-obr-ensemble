package com.chacetech.serviceproviders.common.dao;

import com.chacetech.serviceproviders.common.model.ManagedServiceProvider;

import java.util.List;

public interface IServiceProviderRepository {
    List<ManagedServiceProvider> findByName(String name);
}
