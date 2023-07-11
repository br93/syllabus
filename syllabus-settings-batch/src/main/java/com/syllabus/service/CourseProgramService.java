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
import com.syllabus.model.CourseProgramModel;
import com.syllabus.repository.CourseProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseProgramService {

	private final CourseProgramRepository courseProgramRepository;
	private final ProgramService programService;
	private final CourseService courseService;
	private final CourseTypeService courseTypeService;

	 public List<CourseProgramModel> csvToCourseProgram(InputStream inputStream) {
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

	public void save(MultipartFile file) {
		try {
			List<CourseProgramModel> coursePrograms = this.csvToCourseProgram(file.getInputStream());
			saveFromList(coursePrograms);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	private void saveFromList(List<CourseProgramModel> list) {
		Integer size = list.size();

		for (int i = 0; i < size; i++) {
			courseProgramRepository.save(list.get(i));
		}
	}

	public CourseProgramModel findByCourseProgramId(String id) {
		return courseProgramRepository.findByCourseProgramId(id)
				.orElseThrow(() -> new EntityNotFoundException("course program not found"));
	}
}
