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
import com.syllabus.model.UniversityModel;

@Component
public class UniversityCsvConverter implements CsvConverter<UniversityModel> {

    @Override
    public List<UniversityModel> csvToModel(InputStream inputStream) {
        List<UniversityModel> universities = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
        String[] headers = { "Name", "Code" };

        for (CSVRecord csvRecord : csvRecords) {

            UniversityModel university = new UniversityModel(null, Instant.now(), Instant.now(), null,
                    UUID.randomUUID().toString(),
                    csvRecord.get(headers[0]),
                    csvRecord.get(headers[1]));
            universities.add(university);

        }

        return universities;
    }

}
