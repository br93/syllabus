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
import com.syllabus.model.ProgramModel;
import com.syllabus.repository.ProgramRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramService {

	private final ProgramRepository programRepository;
	private final UniversityService universityService;

	public List<ProgramModel> csvToProgram(InputStream inputStream) {
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

	public void save(MultipartFile file) {
		try {
			List<ProgramModel> programs = this.csvToProgram(file.getInputStream());
			programRepository.saveAll(programs);
		} catch (IOException ex) {
			throw new CsvException("fail to store csv data: " + ex.getMessage());
		}
	}

	public ProgramModel findByProgramCode(String code) {
		return programRepository.findByProgramCode(code)
				.orElseThrow(() -> new EntityNotFoundException("program " + code + " not found"));
	}
}
