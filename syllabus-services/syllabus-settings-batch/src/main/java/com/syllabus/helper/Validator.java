package com.syllabus.helper;

public class Validator {

    private Validator(){}

    public static boolean isEmpty(String value) {
        return (value == null || value.isEmpty() || value.isBlank());
    }

}
