package com.syllabus.exception;

public class PreRequisiteException extends RuntimeException {

    public PreRequisiteException(String message){
        super(message);
    }

    public PreRequisiteException(Throwable cause){
        super(cause);
    }

    public PreRequisiteException(String message, Throwable cause){
        super(message, cause);
    }
    
}
