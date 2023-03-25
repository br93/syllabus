package com.syllabus.exception;

public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException(String message){
        super(message);
    }

    public UserNotAuthorizedException(Throwable cause){
        super(cause);
    }

    public UserNotAuthorizedException(String message, Throwable cause){
        super(message, cause);
    }
    
}

