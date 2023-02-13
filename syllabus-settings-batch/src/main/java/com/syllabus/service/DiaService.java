package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.DiaModel;
import com.syllabus.repository.DiaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaService {
	
	private final DiaRepository diaRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<DiaModel> dias = csvHelper.csvToDia(file.getInputStream());
			diaRepository.saveAll(dias);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}
