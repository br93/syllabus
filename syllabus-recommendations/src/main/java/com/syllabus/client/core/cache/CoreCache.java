package com.syllabus.client.core.cache;

import java.util.List;

import com.syllabus.client.core.CoreResponse;

public interface CoreCache {
    
    public List<CoreResponse> getCachedMissingRequiredCoursesByUserId(String userId);
    public List<CoreResponse> getCachedMissingElectiveCoursesByUserId(String userId);
    public List<CoreResponse> getCachedCoursesTakenByUserId(String userId);
}
