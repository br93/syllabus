package com.syllabus.exception;

public class ProgramNotFoundException extends RuntimeException {

    public ProgramNotFoundException(String message){
        super(message);
    }

    public ProgramNotFoundException(Throwable cause){
        super(cause);
    }

    public ProgramNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    
}

