package com.syllabus.client.settings.cache;

import java.util.List;

import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;

public interface SettingsCache {

    public PreRequisiteCountResponse getCachedAsPreRequisiteCountByCourseCode(String courseCode);
    public PreRequisiteCoursesResponse getCachedPreRequisitesByCourseCode(String courseCode);
    public CourseClassesResponse getCachedClassesByCourse(String courseCode);
    public List<ClassScheduleResponse> getCachedClassSchedulesByClassCode(String courseCode);

}
