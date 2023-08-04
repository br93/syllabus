package com.syllabus.exception;

public class CustomCallNotPermittedException extends RuntimeException {

    public CustomCallNotPermittedException(String message){
        super(message);
    }

    public CustomCallNotPermittedException(Throwable cause){
        super(cause);
    }

    public CustomCallNotPermittedException(String message, Throwable cause){
        super(message, cause);
    }
    
}
