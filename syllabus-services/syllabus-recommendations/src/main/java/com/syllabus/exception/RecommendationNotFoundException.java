package com.syllabus.exception;

public class RecommendationNotFoundException extends RuntimeException {

    public RecommendationNotFoundException(String message){
        super(message);
    }

    public RecommendationNotFoundException(Throwable cause){
        super(cause);
    }

    public RecommendationNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}