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
import com.syllabus.model.ClassModel;
import com.syllabus.service.impl.CourseService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClassCsvConverter implements CsvConverter<ClassModel>{

    private final CourseService courseService;

    public List<ClassModel> csvToModel(InputStream inputStream) {
		List<ClassModel> classes = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Course", "Code" };

		for (CSVRecord csvRecord : csvRecords) {

			ClassModel classModel = new ClassModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					courseService.findByCourseCode(csvRecord.get(headers[0])),
					csvRecord.get(headers[1]));
			classes.add(classModel);

		}

		return classes;
	}
    
}
