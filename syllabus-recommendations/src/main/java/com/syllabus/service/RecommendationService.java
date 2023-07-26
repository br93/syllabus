package com.syllabus.service;

import org.springframework.stereotype.Service;

import com.syllabus.client.core.CoreClient;
import com.syllabus.client.settings.SettingsClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendationService {
    
    private final CoreClient coreClient;
    private final SettingsClient settingsClient;

}
