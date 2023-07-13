package com.syllabus.exception;

public class UserUnauthorizedException extends RuntimeException {

    public UserUnauthorizedException(String message){
        super(message);
    }

    public UserUnauthorizedException(Throwable cause){
        super(cause);
    }

    public UserUnauthorizedException(String message, Throwable cause){
        super(message, cause);
    }
    
}

