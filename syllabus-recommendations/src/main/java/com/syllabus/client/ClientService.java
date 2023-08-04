package com.syllabus.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.cache.CacheConstant;
import com.syllabus.cache.CacheRepository;
import com.syllabus.client.core.CoreClient;
import com.syllabus.client.core.CoreResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;
import com.syllabus.client.students.StudentResponse;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.unmarshal.impl.ClassScheduleUnmarshal;
import com.syllabus.unmarshal.impl.CoreResponseUnmarshal;
import com.syllabus.unmarshal.impl.CourseClassesUnmarshal;
import com.syllabus.unmarshal.impl.PreRequisiteCountUnmarshal;
import com.syllabus.unmarshal.impl.PreRequisiteCoursesUnmarshal;
import com.syllabus.unmarshal.impl.StudentUnmarshal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final CacheRepository cacheRepository;
    
    private final CoreClient coreClient;
    private final SettingsClient settingsClient;
    private final StudentsClient studentsClient;

    private final ClassScheduleUnmarshal classScheduleUnmarshal;
    private final CoreResponseUnmarshal coreUnmarshal;
    private final CourseClassesUnmarshal coursesClassesUnmarshal;
    private final PreRequisiteCountUnmarshal preRequisiteCountUnmarshal;
    private final PreRequisiteCoursesUnmarshal preRequisiteCoursesUnmarshal;
    private final StudentUnmarshal studentUnmarshal;

    public CourseClassesResponse getClassesByCourse(String courseCode){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.CLASSES, courseCode);

        if (cacheRepository.hasKey(cacheId))
            return coursesClassesUnmarshal.toResponse(cacheRepository.getCachedData(cacheId));

        return settingsClient.getClassesByCourse(courseCode);
    }

    public List<ClassScheduleResponse> getClassSchedulesByClassCode(String classCode){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.CLASS_SCHEDULES, classCode);

        if (cacheRepository.hasKey(cacheId))
            return classScheduleUnmarshal.toList(cacheRepository.getCachedData(cacheId));

        return settingsClient.getClassSchedulesByClassCode(classCode);
    }

    public StudentResponse getStudentByUserId(String userId){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.STUDENTS, userId);

        if(cacheRepository.hasKey(cacheId))
            return studentUnmarshal.toResponse(cacheRepository.getCachedData(cacheId));
        return studentsClient.getStudentByUserId(userId);
    }

    public List<CoreResponse> getCoursesTakenByUserId(String userId){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.COURSES_TAKEN, userId);

        if(cacheRepository.hasKey(cacheId))
            return coreUnmarshal.toList(cacheRepository.getCachedData(cacheId));

        return coreClient.getCoursesTakenByUserId(userId).toList();

    }

    public List<CoreResponse> getMissingRequiredCoursesByUserId(String userId){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.MISSING_REQUIRED, userId);

        if(cacheRepository.hasKey(cacheId))
            return coreUnmarshal.toList(cacheRepository.getCachedData(cacheId));
        return coreClient.getMissingRequiredCoursesByUserId(userId).get().collect(Collectors.toCollection(ArrayList::new));
    }

    public List<CoreResponse> getMissingElectiveCoursesByUserId(String userId){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.MISSING_ELECTIVE, userId);

        if(cacheRepository.hasKey(cacheId))
            return coreUnmarshal.toList(cacheRepository.getCachedData(cacheId));
        return coreClient.getMissingElectiveCoursesByUserId(userId).get().collect(Collectors.toCollection(ArrayList::new));
    }

    public PreRequisiteCoursesResponse getPreRequisitesByCourseCode(String courseCode){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.PRE_REQUISITES, courseCode);

        if(cacheRepository.hasKey(cacheId))
            return preRequisiteCoursesUnmarshal.toResponse(cacheId);
        return settingsClient.getPreRequisitesByCourseCode(courseCode);
    }

    public PreRequisiteCountResponse getAsPreRequisiteCountByCourseCode(String courseCode){

        var cacheId = cacheRepository.generateCacheId(CacheConstant.PRE_REQUISITES_COUNT, courseCode);

        if(cacheRepository.hasKey(cacheId))
            return preRequisiteCountUnmarshal.toResponse(cacheId);
        return settingsClient.getAsPreRequisiteCountByCourseCode(courseCode);
    }
    
}
