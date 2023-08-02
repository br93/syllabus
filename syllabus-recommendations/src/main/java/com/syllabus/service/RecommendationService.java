package com.syllabus.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.response.ClassResponse;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.data.model.RecommendationModel;
import com.syllabus.data.response.RecommendationResponse;
import com.syllabus.data.response.StudentDataResponse;
import com.syllabus.mapper.RecommendationMapper;
import com.syllabus.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final StudentDataService studentDataService;

    private final ClientService clientService;

    private final RecommendationMapper recommendationMapper;

    public RecommendationResponse createRecommendation(String userId, Boolean isRequired, List<Boolean> schedules, Integer workload) {

        var studentData = studentDataService.getRecentStudentDataByUserId(userId);
        List<String> courses = this.getCorrectTypeOfRecommendation(studentData, isRequired);
        
        List<String> validClasses = new ArrayList<>();
        Set<String> uniqueSchedules = new HashSet<>();

        courses.forEach(course -> {
            var classes = clientService.getClassesByCourse(course).getClasses().stream()
                    .map(ClassResponse::getClassCode)
                    .collect(Collectors.toCollection(HashSet::new));
            this.addClass(classes, validClasses, schedules, workload, course, uniqueSchedules);
        });
        
        var recommendation =  recommendationRepository.save(new RecommendationModel(null, userId, 
                new HashSet<>(validClasses), validClasses.size(), schedules.get(0), schedules.get(1), schedules.get(2), isRequired, 
                Instant.now(), Instant.now(), null));

        return recommendationMapper.toResponse(recommendation);

    }

    private void addClass(Set<String> classes, List<String> validClasses, List<Boolean> schedules, Integer workload, String course, Set<String> uniqueSchedules) {

        List<String> scheduleStrings = Arrays.asList("M", "T", "N");

        classes.forEach(classString -> {
            var classSchedules = clientService.getClassSchedulesByClassCode(classString).stream()
                    .collect(Collectors.toCollection(HashSet::new));

            this.validatingClassWithSchedule(schedules.get(0), scheduleStrings.get(0), classSchedules, validClasses, workload, course, uniqueSchedules);
            this.validatingClassWithSchedule(schedules.get(1), scheduleStrings.get(1), classSchedules, validClasses, workload, course, uniqueSchedules);
            this.validatingClassWithSchedule(schedules.get(2), scheduleStrings.get(2), classSchedules, validClasses, workload, course, uniqueSchedules);

        });
    }

    private void validatingClassWithSchedule(boolean schedule, String scheduleString, Set<ClassScheduleResponse> classSchedules, List<String> validClasses,
            Integer workload, String course, Set<String> uniqueSchedules) {

        if (schedule && validClasses.size() < workload) 
            this.addValidClasses(classSchedules, workload, validClasses, scheduleString, course, uniqueSchedules);
    }

    private void addValidClasses(Set<ClassScheduleResponse> classSchedules, Integer workload, List<String> validClasses, String scheduleString, String course, Set<String> uniqueSchedules) {
        
        long filteredClasses = this.filterByTimeOfDay(classSchedules, scheduleString).count();

        if(filteredClasses > 0){

            List<String> classes = this.mapToClassCode(classSchedules).collect(Collectors.toList());
            List<String> scheduleList = this.mapToSchedule(classSchedules).collect(Collectors.toCollection(ArrayList::new));

            int size = classSchedules.size();
        
            if (validClasses.size() + size < workload) {
                validClasses.addAll(classes);
                this.removeDuplicates(validClasses, course);
                this.removeInvalidClassesWithSameSchedule(scheduleList, classes, validClasses, uniqueSchedules);
            }

        }
       
    }

    private Stream<ClassScheduleResponse> filterByTimeOfDay(Set<ClassScheduleResponse> classSchedules, String scheduleString) {
        return classSchedules.stream().filter(classSchedule -> classSchedule.getTimeOfDay().equals(scheduleString));
    }

    private Stream<String> mapToClassCode(Set<ClassScheduleResponse> classSchedule) {
        return classSchedule.stream().map(ClassScheduleResponse::getClassCode);
    }

    private Stream<String> mapToSchedule(Set<ClassScheduleResponse> classSchedule) {
        return classSchedule.stream().map(ClassScheduleResponse::getSchedule);
    }

    private void removeInvalidClassesWithSameSchedule(List<String> newSchedules, List<String> newClasses, List<String> validClasses, Set<String> uniqueSchedules) {
        
        int initialSize = uniqueSchedules.size();
        uniqueSchedules.addAll(newSchedules);
        int delta = uniqueSchedules.size() - initialSize;

        if (delta == 0 || delta < newSchedules.size())
            validClasses.removeAll(newClasses);
    }

    private void removeDuplicates(List<String> validClasses, String course) {

        Set<String> uniqueClasses = this.filterByCourse(validClasses, course).collect(Collectors.toSet());

        if (uniqueClasses.size() > 1) {

            List<String> listNonDuplicates = new ArrayList<>(uniqueClasses);
            Collections.shuffle(listNonDuplicates);
            listNonDuplicates.remove(0);

            validClasses.removeIf(uniqueClasses::contains);
        }
    }

    private Stream<String> filterByCourse(List<String> validClasses, String course){
        return validClasses.stream().filter(validClass -> validClass.contains(course));
    }

    private List<String> getCorrectTypeOfRecommendation(StudentDataResponse studentData, boolean required) {
        if (required)
            return this.convertMapToList(studentData.getRequiredCourses());
        return this.convertMapToList(studentData.getElectiveCourses());
    }

    private List<String> convertMapToList(Map<String, Double> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
