package com.chacetech.serviceproviders.exception;

public class ServiceProviderException extends Exception {

    public ServiceProviderException() {super(); }

    public ServiceProviderException(String message) { super(message); }

    public ServiceProviderException(String message, Throwable throwable) {super(message, throwable); }
}
