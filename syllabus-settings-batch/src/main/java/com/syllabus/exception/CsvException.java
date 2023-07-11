package com.syllabus.exception;

public class CsvException extends RuntimeException {

    public CsvException(String message){
        super(message);
    }

    public CsvException(Throwable cause){
        super(cause);
    }

    public CsvException(String message, Throwable cause){
        super(message, cause);
    }
    
}
