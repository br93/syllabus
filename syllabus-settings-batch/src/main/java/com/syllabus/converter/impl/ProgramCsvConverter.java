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
import com.syllabus.model.ProgramModel;
import com.syllabus.service.impl.UniversityService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProgramCsvConverter implements CsvConverter<ProgramModel> {

    private final UniversityService universityService;

    public List<ProgramModel> csvToModel(InputStream inputStream) {
        List<ProgramModel> programs = new ArrayList<>();
        Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
        String[] headers = { "Name", "Code", "Terms", "University" };

        for (CSVRecord csvRecord : csvRecords) {

            ProgramModel program = new ProgramModel(null, Instant.now(), Instant.now(), null,
                    UUID.randomUUID().toString(),
                    csvRecord.get(headers[0]), csvRecord.get(headers[1]),
                    Short.valueOf(csvRecord.get(headers[2])),
                    universityService.findByUniversityCode(csvRecord.get(headers[3])));
            programs.add(program);

        }

        return programs;
    }

}
