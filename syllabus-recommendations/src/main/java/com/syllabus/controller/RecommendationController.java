package com.syllabus.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.data.model.RecommendationModel;
import com.syllabus.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("{id}")
    public ResponseEntity<RecommendationModel> createRecommendation(@PathVariable(name = "id") String userId,
            @RequestParam(name = "required", defaultValue = "true") Boolean isRequired,
            @RequestParam(name = "morning", defaultValue = "false") Boolean morning,
            @RequestParam(name = "afternoon", defaultValue = "false") Boolean afternoon,
            @RequestParam(name = "night", defaultValue = "false") Boolean night,
            @RequestParam(name = "workload") Integer workload) {
        List<Boolean> schedules = Arrays.asList(morning, afternoon, night);

        return new ResponseEntity<>(recommendationService.createRecommendation(userId, isRequired, schedules, workload),
                HttpStatus.OK);
    }
}
