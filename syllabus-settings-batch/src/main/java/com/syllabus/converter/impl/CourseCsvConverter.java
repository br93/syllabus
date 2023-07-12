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
import com.syllabus.model.CourseModel;
import com.syllabus.service.impl.UniversityService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CourseCsvConverter implements CsvConverter<CourseModel>{

	private final UniversityService universityService;

    public List<CourseModel> csvToModel(InputStream inputStream) {
		List<CourseModel> courses = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Name", "Code", "Workload", "University" };

		for (CSVRecord csvRecord : csvRecords) {

			CourseModel course = new CourseModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), csvRecord.get(headers[0]), csvRecord.get(headers[1]),
					Short.valueOf(csvRecord.get(headers[2])), null, null,
					universityService.findByUniversityCode(csvRecord.get(headers[3])));

			courses.add(course);

		}

		return courses;
	}
    
}
