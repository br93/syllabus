package com.syllabus.client.students.cache;

import com.syllabus.client.students.StudentResponse;

public interface StudentsCache {

    public StudentResponse getCachedStudentByUserId(String userId);
    
}
