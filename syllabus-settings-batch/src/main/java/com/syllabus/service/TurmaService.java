package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.TurmaModel;
import com.syllabus.repository.TurmaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TurmaService {
	
	private final TurmaRepository turmaRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<TurmaModel> turmas = csvHelper.csvToTurma(file.getInputStream());
			turmaRepository.saveAll(turmas);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}
