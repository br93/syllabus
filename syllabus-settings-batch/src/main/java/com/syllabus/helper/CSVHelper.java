package com.syllabus.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.model.CursoModel;
import com.syllabus.model.DiaModel;
import com.syllabus.model.HorarioModel;
import com.syllabus.model.TipoModel;
import com.syllabus.model.TurnoModel;

public class CSVHelper {

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}
	
	public static List<TurnoModel> csvToTurno(InputStream inputStream) {
		List<TurnoModel> turnos = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = {"Nome", "Valor"};

		for (CSVRecord record : records) {
			
			TurnoModel turno = new TurnoModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), Short.valueOf(record.get(headers[1])));
			turnos.add(turno);
			
		}

		return turnos;
	}
	
	public static List<TipoModel> csvToTipo(InputStream inputStream) {
		List<TipoModel> tipos = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = {"Nome", "Valor"};

		for (CSVRecord record : records) {
			
			TipoModel tipo = new TipoModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), Short.valueOf(record.get(headers[1])));
			tipos.add(tipo);
			
		}

		return tipos;
	}
	
	public static List<HorarioModel> csvToHorario(InputStream inputStream) {
		List<HorarioModel> horarios = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = {"Sigla", "Faixa"};

		for (CSVRecord record : records) {
			
			HorarioModel horario = new HorarioModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), record.get(headers[1]));
			horarios.add(horario);
			
		}

		return horarios;
	}
	
	public static List<DiaModel> csvToDia(InputStream inputStream) {
		List<DiaModel> dias = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = {"Nome", "Numero"};

		for (CSVRecord record : records) {
			
			DiaModel dia = new DiaModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), Short.valueOf(record.get(headers[1])));
			dias.add(dia);
			
		}

		return dias;
	}
	
	public static List<CursoModel> csvToCurso(InputStream inputStream) {
		List<CursoModel> cursos = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = {"Nome", "Codigo", "Periodos"};

		for (CSVRecord record : records) {
			
			CursoModel curso = new CursoModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), record.get(headers[1]), Short.valueOf(record.get(headers[2])));
			cursos.add(curso);
			
		}

		return cursos;
	}

	private static Iterable<CSVRecord> readFile(InputStream inputStream) {

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true)
					.setIgnoreHeaderCase(true).setTrim(true).build();

			Iterable<CSVRecord> csvRecord = csvFormat.parse(fileReader);
			return csvRecord;
		} catch (IOException ex) {
			throw new RuntimeException("fail to parse CSV file: " + ex.getMessage());
		}

	}

	

}
