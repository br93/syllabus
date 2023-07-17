package com.syllabus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentsClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreService {

    private final StudentsClient studentsClient;
    private final SettingsClient settingsClient;

    public List<CourseProgramResponse> getCoursesTaken(String userId) {
        var student = studentsClient.getStudentByUserId(userId);

        return settingsClient.getCourseProgramsByCourseCodeIn(student.getCourseCodes());
    }
    
}
