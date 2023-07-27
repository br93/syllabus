package com.syllabus.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.client.core.CoreClient;
import com.syllabus.client.core.CoreResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.response.PreRequisiteResponse;
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

        var coursesTaken = coreClient.getCoursesTaken(userId).toList();

        var missingRequiredCourses = coreClient.getMissingRequiredCourses(userId).stream()
                .collect(Collectors.toCollection(ArrayList::new));
        Map<String, Double> requiredCourses = this.formatCourses(coursesTaken, missingRequiredCourses, studentTerm);

        var missingElectiveCourses = coreClient.getMissingElectiveCourses(userId).stream()
                .collect(Collectors.toCollection(ArrayList::new));
        Map<String, Double> electiveCourses = this.formatCourses(coursesTaken, missingElectiveCourses, studentTerm);

        StudentDataModel studentData = new StudentDataModel(null, userId, 
            coursesTaken.size(), studentTerm, requiredCourses, electiveCourses,
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

    private Map<String, Double> formatCourses(List<CoreResponse> coursesTaken, List<CoreResponse> missingCourses,
            Short studentTerm) {
        
        this.removeIfPreRequisiteMissing(coursesTaken, missingCourses);
        return this.calculateCoursesValues(missingCourses, studentTerm);
    }

    private void removeIfPreRequisiteMissing(List<CoreResponse> coursesTaken, List<CoreResponse> missingCourses) {

        List<String> listCoursesTaken = coursesTaken.stream().map(CoreResponse::getCourseCode)
                .collect(Collectors.toList());
                
        List<CoreResponse> coursesToRemove = new ArrayList<>();

        missingCourses.forEach(course -> this.checkPreRequisite(course, listCoursesTaken, coursesToRemove));
        missingCourses.removeAll(coursesToRemove);
    }

    private void checkPreRequisite(CoreResponse course, List<String> listCoursesTaken,
            List<CoreResponse> coursesToRemove) {

        var preRequisites = settingsClient.getPreRequisites(course.getCourseCode()).getPreRequisites();

        if (!preRequisites.isEmpty()) {
            var list = preRequisites.stream().map(PreRequisiteResponse::getCourseCode)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (!listCoursesTaken.containsAll(list))
                coursesToRemove.add(course);
        }
    }

    private Map<String, Double> calculateCoursesValues(List<CoreResponse> courses, Short studentTerm) {
        Map<String, Double> mappedCourses = new HashMap<>();

        courses.forEach(course -> {
            var courseTerm = course.getTerm();
            var preRequisiteCount = settingsClient.getAsPreRequisiteCount(course.getCourseCode()).getCount();

            var factor = this.getTermFactor(studentTerm, courseTerm);
            mappedCourses.put(course.getCourseCode(), factor + preRequisiteCount * factor);
        });

        return mappedCourses;
    }

    private Double getTermFactor(Short studentTerm, Short courseTerm) {

        var term = studentTerm - courseTerm;

        if (term == 0)
            return 1.5;
        if (term > 0)
            return 2.0;
        return 0.8;

    }

}
