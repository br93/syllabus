package com.syllabus.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.helper.CSVHelper;
import com.syllabus.model.TurnoModel;
import com.syllabus.repository.TurnoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TurnoService {
	
	private final TurnoRepository turnoRepository;

	public void save(MultipartFile file){
		try {
			List<TurnoModel> turnos = CSVHelper.csvToTurno(file.getInputStream());
			turnoRepository.saveAll(turnos);
		} catch (IOException ex) {
			throw new RuntimeException("fail to store csv data: " + ex.getMessage());
		}
	}
}
