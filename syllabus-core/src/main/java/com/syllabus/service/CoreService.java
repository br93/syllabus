package com.syllabus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.util.ConstantUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreService {

    private final StudentsClient studentsClient;
    private final SettingsClient settingsClient;
    
    private final ConstantUtil constantUtil;

    public List<CourseProgramResponse> getCoursesTaken(String userId) {
        var student = studentsClient.getStudentByUserId(userId);

        return settingsClient.getCourseProgramsByCourseCodeIn(student.getCourseCodes());
    }

    public List<CourseProgramResponse> getAllRequiredCourses(String userId) {
        var student = studentsClient.getStudentByUserId(userId);
        var program = student.getProgramCode().toString();

        return settingsClient.getCourseProgramsByProgramAndCourseType(program, constantUtil.getRequiredType());
    }

    public List<CourseProgramResponse> getMissingRequiredCourses(String userId) {
        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> x.getType().equals(constantUtil.getRequiredType())).collect(Collectors.toList());
        var allRequiredCourses = this.getAllRequiredCourses(userId);

        allRequiredCourses.removeAll(coursesTaken);

        return allRequiredCourses;
    }

}
