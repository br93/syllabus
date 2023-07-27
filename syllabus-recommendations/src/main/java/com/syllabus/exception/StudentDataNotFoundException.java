package com.syllabus.exception;

public class StudentDataNotFoundException extends RuntimeException {

    public StudentDataNotFoundException(String message){
        super(message);
    }

    public StudentDataNotFoundException(Throwable cause){
        super(cause);
    }

    public StudentDataNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    
}

