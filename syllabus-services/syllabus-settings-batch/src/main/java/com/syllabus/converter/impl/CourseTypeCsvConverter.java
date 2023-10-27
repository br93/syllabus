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
import com.syllabus.model.CourseTypeModel;

@Component
public class CourseTypeCsvConverter implements CsvConverter<CourseTypeModel> {

	public List<CourseTypeModel> csvToModel(InputStream inputStream) {
		List<CourseTypeModel> courseTypes = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Name", "Value" };

		for (CSVRecord csvRecord : csvRecords) {

			CourseTypeModel courseType = new CourseTypeModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					csvRecord.get(headers[0]),
					Short.valueOf(csvRecord.get(headers[1])));
			courseTypes.add(courseType);

		}

		return courseTypes;
	}

}
