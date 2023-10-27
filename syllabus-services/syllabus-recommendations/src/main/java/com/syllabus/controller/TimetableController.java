package com.syllabus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.data.response.RecommendationTimetable;
import com.syllabus.service.TimetableService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/recommendations/timetable")
public class TimetableController {

    private final TimetableService timetableService;

    @GetMapping
    public ResponseEntity<List<RecommendationTimetable>> getRecentTimetableByUserId(
            @RequestParam(name = "user_id", required = false) String userId,
            @RequestParam(name = "recommendation_id", required = false) String recommendationId) {

        if (userId != null)
            return new ResponseEntity<>(timetableService.getRecentTimetableByUserId(userId), HttpStatus.OK);
        return new ResponseEntity<>(timetableService.getTimetableByRecommendationId(recommendationId), HttpStatus.OK);
    }

}
