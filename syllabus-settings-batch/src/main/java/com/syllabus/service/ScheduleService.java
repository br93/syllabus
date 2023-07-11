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
import com.syllabus.model.ScheduleModel;
import com.syllabus.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;

	public List<ScheduleModel> csvToSchedule(InputStream inputStream) {
		List<ScheduleModel> schedules = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Code", "TimeOfDay", "Range" };

		for (CSVRecord csvRecord : csvRecords) {

			ScheduleModel schedule = new ScheduleModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					csvRecord.get(headers[0]),
					csvRecord.get(headers[1]),
					csvRecord.get(headers[2]));
			schedules.add(schedule);

		}

		return schedules;
	}

	public void save(MultipartFile file) {
		try {
			List<ScheduleModel> schedules = this.csvToSchedule(file.getInputStream());
			scheduleRepository.saveAll(schedules);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ScheduleModel findByScheduleCode(String code) {
		return scheduleRepository.findByScheduleCode(code)
				.orElseThrow(() -> new EntityNotFoundException("schedule " + code + " not found"));
	}
}
