package com.syllabus.converter.impl;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.syllabus.converter.CsvConverter;
import com.syllabus.helper.CsvHelper;
import com.syllabus.model.CourseModel;
import com.syllabus.model.PreRequisiteModel;
import com.syllabus.service.impl.CourseService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PreRequisiteCsvConverter implements CsvConverter<PreRequisiteModel> {

    private final CourseService courseService;

    public List<PreRequisiteModel> csvToModel(InputStream inputStream) {
        Set<PreRequisiteModel> preRequisites = new HashSet<>();
        Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
        String[] headers = { "Code", "PreReq1", "PreReq2", "PreReq3" };

        for (CSVRecord csvRecord : csvRecords)
            parseColumns(csvRecord.get(headers[0]), csvRecord.get(headers[1]), csvRecord.get(headers[2]),
                    csvRecord.get(headers[3]), preRequisites);

        return preRequisites.stream().collect(Collectors.toList());
    }

    private PreRequisiteModel createPreRequisite(String courseCode, String preRequisiteCode) {

        if (preRequisiteCode == null || preRequisiteCode.isEmpty() || preRequisiteCode.isBlank())
            return new PreRequisiteModel();

        CourseModel course = courseService.findByCourseCode(courseCode);
        CourseModel preRequisite = courseService.findByCourseCode(preRequisiteCode);

        return new PreRequisiteModel(course, preRequisite);

    }

    private void parseColumns(String code, String preReq1, String preReq2, String preReq3,
            Set<PreRequisiteModel> set) {

        set.add(this.createPreRequisite(code, preReq1));
        set.add(this.createPreRequisite(code, preReq2));
        set.add(this.createPreRequisite(code, preReq3));

        this.trimSet(set);

    }

    private void trimSet(Set<PreRequisiteModel> set) {
        var nullPreRequisite = new PreRequisiteModel();

        set.remove(nullPreRequisite);
    }
}
