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
import com.syllabus.model.ClassScheduleModel;
import com.syllabus.repository.ClassScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClassScheduleService {

	private final ClassScheduleRepository classScheduleRepository;
	private final ClassService classService;
	private final DayService dayService;
	private final ScheduleService scheduleService;

	public List<ClassScheduleModel> csvToClassSchedule(InputStream inputStream) {
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

	public void save(MultipartFile file) {
		try {
			List<ClassScheduleModel> classSchedules = this.csvToClassSchedule(file.getInputStream());
			classScheduleRepository.saveAll(classSchedules);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ClassScheduleModel findByClassScheduleId(String id) {
		return classScheduleRepository.findByClassScheduleId(id)
				.orElseThrow(() -> new EntityNotFoundException("class schedule not found"));
	}
}
