package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.HorarioModel;
import com.syllabus.repository.HorarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HorarioService {
	
	private final HorarioRepository horarioRepository;
	private final CSVHelper csvHelper;

	public void save(MultipartFile file){
		try {
			List<HorarioModel> horarios = csvHelper.csvToHorario(file.getInputStream());
			horarioRepository.saveAll(horarios);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}
