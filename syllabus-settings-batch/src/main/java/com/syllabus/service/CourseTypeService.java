package com.syllabus.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.exception.CsvException;
import com.syllabus.exception.EntityNotFoundException;
import com.syllabus.helper.CsvHelper;
import com.syllabus.model.CourseTypeModel;
import com.syllabus.repository.CourseTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseTypeService {

	private final CourseTypeRepository courseTypeRepository;
	
	public List<CourseTypeModel> csvToCourseType(InputStream inputStream) {
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

	public void save(MultipartFile file) {
		try {
			List<CourseTypeModel> courseTypes = this.csvToCourseType(file.getInputStream());
			courseTypeRepository.saveAll(courseTypes);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public CourseTypeModel findByTypeName(String name) {
		return courseTypeRepository.findByTypeName(name)
				.orElseThrow(() -> new EntityNotFoundException("type " + name + " not found"));
	}
}
