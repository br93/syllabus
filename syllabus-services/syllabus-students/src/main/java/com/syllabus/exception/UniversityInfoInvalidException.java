package com.syllabus.exception;

public class UniversityInfoInvalidException extends RuntimeException {

    public UniversityInfoInvalidException(String message){
        super(message);
    }

    public UniversityInfoInvalidException(Throwable cause){
        super(cause);
    }

    public UniversityInfoInvalidException(String message, Throwable cause){
        super(message, cause);
    }
    
}

