package com.Eapi.scalable.exception;

public class ResourceNotFountException extends RuntimeException{//for http 404
    public ResourceNotFountException(String message){
        super(message);
    }
}
