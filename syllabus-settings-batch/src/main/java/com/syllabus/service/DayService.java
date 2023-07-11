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
import com.syllabus.model.DayModel;
import com.syllabus.repository.DayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DayService {

	private final DayRepository dayRepository;

	public List<DayModel> csvToDay(InputStream inputStream) {
		List<DayModel> days = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Name", "Number" };

		for (CSVRecord csvRecord : csvRecords) {

			DayModel day = new DayModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					csvRecord.get(headers[0]), Short.valueOf(csvRecord.get(headers[1])));
			days.add(day);

		}

		return days;
	}

	public void save(MultipartFile file) {
		try {
			List<DayModel> days = this.csvToDay(file.getInputStream());
			dayRepository.saveAll(days);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public DayModel findByDayNumber(Short number) {
		return dayRepository.findByDayNumber(number)
				.orElseThrow(() -> new EntityNotFoundException("day " + number + " not found"));
	}
}
