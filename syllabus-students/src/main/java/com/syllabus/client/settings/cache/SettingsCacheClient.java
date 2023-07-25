package com.syllabus.client.settings.cache;

import com.syllabus.client.settings.response.ProgramResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;

public interface SettingsCacheClient {
    
    public UniversityCoursesResponse cachedCoursesByUniversity(String universityCode);
    public UniversityResponse cachedUniversityByIdOrCode(String universityCode);
    public ProgramResponse cachedProgramByIdOrCode(String programCode);

    public Boolean isCached(String key);
}
