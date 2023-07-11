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
import com.syllabus.model.ClassModel;
import com.syllabus.repository.ClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassService {

	private final ClassRepository classRepository;
	private final CourseService courseService;

	public List<ClassModel> csvToClass(InputStream inputStream) {
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

	public void save(MultipartFile file) {
		try {
			List<ClassModel> classes = this.csvToClass(file.getInputStream());
			classRepository.saveAll(classes);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ClassModel findByClassCode(String code) {
		return classRepository.findByClassCode(code)
				.orElseThrow(() -> new EntityNotFoundException("class " + code + " not found"));
	}
}
