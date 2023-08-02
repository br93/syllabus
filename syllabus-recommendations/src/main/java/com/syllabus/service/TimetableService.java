package com.syllabus.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.SettingsClient;
import com.syllabus.data.model.RecommendationModel;
import com.syllabus.data.response.RecommendationTimetable;
import com.syllabus.exception.RecommendationNotFoundException;
import com.syllabus.mapper.RecommendationMapper;
import com.syllabus.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TimetableService {
 
    private final RecommendationRepository recommendationRepository;
    private final SettingsClient settingsClient;
    private final RecommendationMapper recommendationMapper;

    public List<RecommendationTimetable> getRecentTimetableByUserId(String userId){
        
        List<RecommendationTimetable> timetable = new ArrayList<>();
        var recommendation = recommendationRepository.findFirstByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(userId).orElseThrow(() -> new RecommendationNotFoundException("recommendation not found"));

        this.generateTimetable(recommendation, timetable);
        
        return timetable;
    }

    public List<RecommendationTimetable> getTimetableByRecommendationId(String recommendationId){

        List<RecommendationTimetable> timetable = new ArrayList<>();
        var recommendation = recommendationRepository.findByRecommendationIdAndDeletedAtIsNull(recommendationId).orElseThrow(() -> new RecommendationNotFoundException("recommendation not found"));

        this.generateTimetable(recommendation, timetable);

        return timetable;
    }

    private void generateTimetable(RecommendationModel recommendation, List<RecommendationTimetable> timetable){
        recommendation.getRecommendation().forEach(classCode-> {
            var classes = settingsClient.getClassSchedulesByClassCode(classCode);
            var classList = classes.stream().map(recommendationMapper::toTimetable).collect(Collectors.toList());
            timetable.addAll(classList);
        });

        this.sortBySchedule(timetable);
    }

    public void sortBySchedule(List<RecommendationTimetable> timetable){
        Comparator<RecommendationTimetable> bySchedule = Comparator.comparing(RecommendationTimetable::getSchedule);
        Collections.sort(timetable, bySchedule);
    }

}
