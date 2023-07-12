package com.syllabus.converter.impl;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.syllabus.converter.CsvConverter;
import com.syllabus.helper.CsvHelper;
import com.syllabus.model.ClassScheduleModel;
import com.syllabus.service.impl.ClassService;
import com.syllabus.service.impl.DayService;
import com.syllabus.service.impl.ScheduleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClassScheduleCsvConverter implements CsvConverter<ClassScheduleModel>{

    private final ClassService classService;
	private final DayService dayService;
	private final ScheduleService scheduleService;

    public List<ClassScheduleModel> csvToModel(InputStream inputStream) {
		List<ClassScheduleModel> classSchedules = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Class", "Day", "Schedule" };

		for (CSVRecord csvRecord : csvRecords) {

			ClassScheduleModel classSchedule = new ClassScheduleModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					classService.findByClassCode(csvRecord.get(headers[0])),
					dayService.findByDayNumber(Short.valueOf(csvRecord.get(headers[1]))),
					scheduleService.findByScheduleCode(csvRecord.get(headers[2])));
			classSchedules.add(classSchedule);

		}

		return classSchedules;
	}
    
    
}
