package com.ms.service.exceptions;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message){
        super(message);
    }
}
