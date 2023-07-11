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
import com.syllabus.model.CourseModel;
import com.syllabus.repository.CourseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;
	private final UniversityService universityService;

	public List<CourseModel> csvToCourse(InputStream inputStream) {
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

	public void save(MultipartFile file) {
		try {
			List<CourseModel> courses = this.csvToCourse(file.getInputStream());
			courseRepository.saveAll(courses);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public CourseModel findByCourseCode(String code) {
		return courseRepository.findByCourseCode(code)
				.orElseThrow(() -> new EntityNotFoundException("course " + code + " not found"));
	}
}
