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

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.response.ClassResponse;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.data.model.RecommendationModel;
import com.syllabus.data.model.StudentDataModel;
import com.syllabus.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final StudentDataService studentDataService;

    private final SettingsClient settingsClient;

    public RecommendationModel createRecommendation(String userId, Boolean isRequired, List<Boolean> schedules,
            Integer workload) {

        var studentData = studentDataService.getRecentStudentDataByUserId(userId);
        List<String> courses = this.getCorrectTypeOfRecommendation(studentData, isRequired);
        List<String> validClasses = new ArrayList<>();

        courses.forEach(course -> {
            var classes = settingsClient.getClassesByCourse(course).getClasses().stream()
                    .map(ClassResponse::getClassCode)
                    .collect(Collectors.toCollection(HashSet::new));
            this.addClass(classes, validClasses, schedules, workload, course);
        });

        return recommendationRepository.save(new RecommendationModel(null, userId, validClasses, validClasses.size(),
                schedules.get(0), schedules.get(1), schedules.get(2), isRequired, Instant.now(), Instant.now(), null));

    }

    private void addClass(Set<String> classes, List<String> validClasses, List<Boolean> schedules, Integer workload,
            String course) {

        List<String> scheduleStrings = Arrays.asList("M", "T", "N");

        classes.forEach(classString -> {
            var classSchedules = settingsClient.getClassSchedulesByClassCode(classString).stream()
                    .collect(Collectors.toCollection(HashSet::new));

            validatingClassWithSchedule(schedules.get(0), scheduleStrings.get(0), classSchedules, validClasses, workload, course);
            validatingClassWithSchedule(schedules.get(1), scheduleStrings.get(1), classSchedules, validClasses, workload, course);
            validatingClassWithSchedule(schedules.get(2), scheduleStrings.get(2), classSchedules, validClasses, workload, course);

        });
    }

    private void validatingClassWithSchedule(boolean schedule, String scheduleString,
            Set<ClassScheduleResponse> classSchedules, List<String> validClasses,
            Integer workload, String course) {

        if (schedule && validClasses.size() < workload) {
            List<String> auxList = classSchedules.stream()
                    .filter(classSchedule -> classSchedule.getTimeOfDay().equals(scheduleString))
                    .map(ClassScheduleResponse::getClassCode)
                    .collect(Collectors.toCollection(ArrayList::new));

            int size = classSchedules.size();
            if (size == auxList.size() && validClasses.size() + size < workload) {
                validClasses.addAll(auxList);
                removeDuplicates(validClasses, course);
            }
        }

    }

    private void removeDuplicates(List<String> classes, String course) {

        Set<String> uniqueClasses = classes.stream().filter(x -> x.contains(course))
                .collect(Collectors.toSet());

        if (uniqueClasses.size() > 1) {

            List<String> listNonDuplicates = new ArrayList<>(uniqueClasses);
            Collections.shuffle(listNonDuplicates);
            listNonDuplicates.remove(0);

            classes.removeIf(uniqueClasses::contains);
        }
    }

    private List<String> getCorrectTypeOfRecommendation(StudentDataModel studentData, boolean required) {
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
