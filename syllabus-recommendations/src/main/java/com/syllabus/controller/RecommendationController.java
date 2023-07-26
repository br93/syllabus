package com.syllabus.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.client.core.CoreResponse;
import com.syllabus.service.RecommendationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/recommendations")
public class RecommendationController {
    
    private final RecommendationService recommendationService;

    @GetMapping("teste/{id}")
    public ResponseEntity<Page<CoreResponse>> teste(@PathVariable String id){
        return new ResponseEntity<>(recommendationService.testCoreClient(id), HttpStatus.OK);
    }
}
