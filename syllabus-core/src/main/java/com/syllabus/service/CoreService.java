package com.syllabus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.util.ConstantUtil;
import com.syllabus.util.Validation;

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
        var requiredCourses = settingsClient.getCourseProgramsByProgramAndCourseType(program,
                constantUtil.getRequiredType());

        if (Validation.hasSecondLayerCourses(program)) {
            requiredCourses.addAll(this.getAllSecondLayerCourses(program));
        }

        return requiredCourses;
    }

    public List<CourseProgramResponse> getMissingRequiredCourses(String userId) {
        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> x.getType().equals(constantUtil.getRequiredType())).collect(Collectors.toList());
        var allRequiredCourses = this.getAllRequiredCourses(userId);

        allRequiredCourses.removeAll(coursesTaken);

        return allRequiredCourses;
    }

    public List<CourseProgramResponse> getAllElectiveCourses(String userId) {
        var student = studentsClient.getStudentByUserId(userId);
        var program = student.getProgramCode().toString();

        return settingsClient.getCourseProgramsByProgramAndNotCourseType(program, constantUtil.getRequiredType());

    }

    public List<CourseProgramResponse> getMissingElectiveCourses(String userId) {
        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> !x.getType().equals(constantUtil.getRequiredType())).collect(Collectors.toList());

        var allElectiveCourses = this.getAllElectiveCourses(userId);

        allElectiveCourses.removeAll(coursesTaken);

        return allElectiveCourses;
    }

    private List<CourseProgramResponse> getAllSecondLayerCourses(String program) {
        return settingsClient.getCourseProgramsByProgramAndCourseType(program, constantUtil.getSecondLayer());
    }

}
