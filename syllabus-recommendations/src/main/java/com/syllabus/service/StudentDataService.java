package com.syllabus.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.client.ClientService;
import com.syllabus.client.core.CoreResponse;
import com.syllabus.client.settings.response.PreRequisiteResponse;
import com.syllabus.data.model.StudentDataModel;
import com.syllabus.data.response.StudentDataResponse;
import com.syllabus.exception.StudentDataNotFoundException;
import com.syllabus.mapper.StudentDataMapper;
import com.syllabus.message.MessageConstant;
import com.syllabus.repository.StudentDataRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentDataService {

    private final StudentDataRepository studentDataRepository;
    private final StudentDataMapper studentDataMapper;

    private final ClientService clientService;

    public StudentDataResponse createData(String userId) {

        var student = clientService.getStudentByUserId(userId);
        final Short studentTerm = student.getTerm().shortValue();

        var coursesTaken = clientService.getCoursesTakenByUserId(userId);

        var missingRequiredCourses = clientService.getMissingRequiredCoursesByUserId(userId);
        Map<String, Double> requiredCourses = this.formatCourses(coursesTaken, missingRequiredCourses, studentTerm);

        var missingElectiveCourses = clientService.getMissingElectiveCoursesByUserId(userId);
        Map<String, Double> electiveCourses = this.formatCourses(coursesTaken, missingElectiveCourses, studentTerm);

        var studentData = studentDataRepository.save(new StudentDataModel(null, userId, 
            coursesTaken.size(), studentTerm, requiredCourses, electiveCourses,
            Instant.now(), Instant.now(), null));

        return studentDataMapper.toResponse(studentData);

    }

    public StudentDataResponse getStudentDataById(String id) {
        var studentData = studentDataRepository.findByStudentDataIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new StudentDataNotFoundException(MessageConstant.STUDENT_NOT_FOUND));

        return studentDataMapper.toResponse(studentData);
    }

    public StudentDataResponse getStudentDataByUserIdAndCoursesTaken(String id, Long coursesTaken) {
        var studentData = studentDataRepository.findByUserIdAndCoursesTakenAndDeletedAtIsNull(id, coursesTaken)
                .orElseThrow(() -> new StudentDataNotFoundException(MessageConstant.STUDENT_NOT_FOUND));

        return studentDataMapper.toResponse(studentData);
    }

    public StudentDataResponse getRecentStudentDataByUserId(String id) {
        var studentData = studentDataRepository.findFirstByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(id)
                .orElseThrow(() -> new StudentDataNotFoundException(MessageConstant.STUDENT_NOT_FOUND));

        return studentDataMapper.toResponse(studentData);
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

        var preRequisites = clientService.getPreRequisitesByCourseCode(course.getCourseCode()).getPreRequisites();

        if (!preRequisites.isEmpty()) {
            var list = preRequisites.stream().map(PreRequisiteResponse::getCourseCode)
                    .collect(Collectors.toList());

            if (!listCoursesTaken.containsAll(list))
                coursesToRemove.add(course);
        }
    }

    private Map<String, Double> calculateCoursesValues(List<CoreResponse> courses, Short studentTerm) {
        Map<String, Double> mappedCourses = new HashMap<>();

        courses.forEach(course -> {
            var courseTerm = course.getTerm();
            var preRequisiteCount = clientService.getAsPreRequisiteCountByCourseCode(course.getCourseCode()).getCount();

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
