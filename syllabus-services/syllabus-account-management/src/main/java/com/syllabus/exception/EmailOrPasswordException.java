package com.syllabus.exception;

public class EmailOrPasswordException extends RuntimeException {

    public EmailOrPasswordException(String message){
        super(message);
    }

    public EmailOrPasswordException(Throwable cause){
        super(cause);
    }

    public EmailOrPasswordException(String message, Throwable cause){
        super(message, cause);
    }
    
}
