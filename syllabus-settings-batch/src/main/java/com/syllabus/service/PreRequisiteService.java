package com.syllabus.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.exception.CsvException;
import com.syllabus.helper.CsvHelper;
import com.syllabus.model.CourseModel;
import com.syllabus.model.PreRequisiteModel;
import com.syllabus.repository.PreRequisiteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreRequisiteService {

    private final CourseService courseService;
    private final PreRequisiteRepository preRequisiteRepository;

    public Set<PreRequisiteModel> csvToPreRequisite(InputStream inputStream) {
        Set<PreRequisiteModel> preRequisites = new HashSet<>();
        Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
        String[] headers = { "Code", "PreReq1", "PreReq2", "PreReq3" };

        for (CSVRecord csvRecord : csvRecords)
            parseColumns(csvRecord.get(headers[0]), csvRecord.get(headers[1]), csvRecord.get(headers[2]),
                    csvRecord.get(headers[3]), preRequisites);

        return preRequisites;
    }

    public void save(MultipartFile file) {
        try {
            Set<PreRequisiteModel> preRequisites = this.csvToPreRequisite(file.getInputStream());
            preRequisiteRepository.saveAll(preRequisites);
        } catch (IOException ex) {
            throw new CsvException("fail to store csv data: " + ex.getMessage());
        }
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
