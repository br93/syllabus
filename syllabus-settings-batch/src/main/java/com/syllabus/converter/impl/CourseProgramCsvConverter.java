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
import com.syllabus.model.CourseProgramModel;
import com.syllabus.service.impl.CourseService;
import com.syllabus.service.impl.CourseTypeService;
import com.syllabus.service.impl.ProgramService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CourseProgramCsvConverter implements CsvConverter<CourseProgramModel>{

    private final ProgramService programService;
	private final CourseService courseService;
	private final CourseTypeService courseTypeService;

    public List<CourseProgramModel> csvToModel(InputStream inputStream) {
		List<CourseProgramModel> coursePrograms = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Term", "Program", "Course", "Type" };

		for (CSVRecord csvRecord : csvRecords) {

			CourseProgramModel courseProgram = new CourseProgramModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					Short.valueOf(csvRecord.get(headers[0])),
					programService.findByProgramCode(csvRecord.get(headers[1])),
					courseService.findByCourseCode(csvRecord.get(headers[2])),
					courseTypeService.findByTypeName(csvRecord.get(headers[3])));
			coursePrograms.add(courseProgram);

		}

		return coursePrograms;
	}
    
}
