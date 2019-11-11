package com.chacetech.serviceproviders.common.exception;

public class ServiceProviderException extends Exception {

    public ServiceProviderException() { super(); }
    public ServiceProviderException(String message) { super(message); }
    public ServiceProviderException(String message, Throwable cause) {super(message, cause); }
    public ServiceProviderException(Throwable cause) { super(cause); }
}