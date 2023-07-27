package com.syllabus.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.syllabus.client.core.CoreClient;
import com.syllabus.client.core.CoreResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.data.model.StudentDataModel;
import com.syllabus.exception.StudentDataNotFoundException;
import com.syllabus.repository.StudentDataRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentDataService {

    private final StudentDataRepository studentDataRepository;

    private final CoreClient coreClient;
    private final SettingsClient settingsClient;
    private final StudentsClient studentsClient;

    public StudentDataModel createData(String userId) {
        var student = studentsClient.getStudentByUserId(userId);
        final Short studentTerm = student.getTerm().shortValue();

        var missingRequiredCourses = coreClient.getMissingRequiredCourses(userId).toList();
        Map<String, Double> requiredCourses = this.calculateCoursesValues(missingRequiredCourses, studentTerm);

        var missingElectiveCourses = coreClient.getMissingElectiveCourses(userId).toList();
        Map<String, Double> electiveCourses = this.calculateCoursesValues(missingElectiveCourses, studentTerm);
        
        var coursesTaken = coreClient.getCoursesTaken(userId).getTotalElements();

        StudentDataModel studentData = new StudentDataModel(null, userId, coursesTaken, studentTerm, requiredCourses, electiveCourses, 
            Instant.now(), Instant.now(), null);

        return studentDataRepository.save(studentData);

    }

    public StudentDataModel getStudentDataModelById(String id) {
        return studentDataRepository.findByStudentDataIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new StudentDataNotFoundException("student data not found"));
    }

    public StudentDataModel getStudentDataModelByUserIdAndCoursesTaken(String id, Long coursesTaken) {
        return studentDataRepository.findByUserIdAndCoursesTakenAndDeletedAtIsNull(id, coursesTaken)
                .orElseThrow(() -> new StudentDataNotFoundException("student data not found"));
    }

    private Double getTermFactor(Short studentTerm, Short courseTerm) {

        var term = studentTerm - courseTerm;

        if (term == 0)
            return 1.5;
        if (term > 0)
            return 2.0;
        return 0.8;

    }

    private Map<String, Double> calculateCoursesValues(List<CoreResponse> courses, Short studentTerm) {
        Map<String, Double> mappedCourses = new HashMap<>();

        courses.forEach(course -> {
            var courseTerm = course.getTerm();
            var preRequisiteCount = settingsClient.getAsPreRequisiteCount(course.getCourseCode()).getCount();

            var factor = getTermFactor(studentTerm, courseTerm);
            mappedCourses.put(course.getCourseCode(), factor + preRequisiteCount * factor);
        });

        return mappedCourses;
    }

}
