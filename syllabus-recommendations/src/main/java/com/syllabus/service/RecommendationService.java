package com.syllabus.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.syllabus.client.core.CoreClient;
import com.syllabus.client.core.CoreResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendationService {
    
    private final CoreClient coreClient;

    public Page<CoreResponse> testCoreClient(String userId){
        return coreClient.getCoursesTaken(userId);
    }
}
