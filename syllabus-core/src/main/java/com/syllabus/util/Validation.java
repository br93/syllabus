package com.syllabus.util;

public class Validation {

    private Validation(){}

    public static boolean hasSecondLayerCourses(String program){
        return program.equals("236");
    }

    public static boolean isValidCache(Object cache){
        return cache != null;
    }
    
}
