package com.syllabus.helper;

public class StringManipulator {

    private StringManipulator(){}
    
    public static String extractSufix(String schedule) {
		return schedule.substring(1);
	}

	public static String extractPrefix(String schedule) {
		return schedule.substring(0, 1);
	}
}
