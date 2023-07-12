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
import com.syllabus.model.DayModel;

@Component
public class DayCsvConverter implements CsvConverter<DayModel> {

    public List<DayModel> csvToModel(InputStream inputStream) {
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
    
}
