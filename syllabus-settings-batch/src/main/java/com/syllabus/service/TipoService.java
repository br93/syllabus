package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.TipoModel;
import com.syllabus.repository.TipoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoService {
	
	private final TipoRepository tipoRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<TipoModel> tipos = csvHelper.csvToTipo(file.getInputStream());
			tipoRepository.saveAll(tipos);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}
