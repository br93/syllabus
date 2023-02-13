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
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.model.CursoModel;
import com.syllabus.model.DiaModel;
import com.syllabus.model.DisciplinaCursoModel;
import com.syllabus.model.DisciplinaModel;
import com.syllabus.model.HorarioAulaModel;
import com.syllabus.model.HorarioModel;
import com.syllabus.model.TipoModel;
import com.syllabus.model.TurmaModel;
import com.syllabus.model.TurnoModel;
import com.syllabus.repository.CursoRepository;
import com.syllabus.repository.DiaRepository;
import com.syllabus.repository.DisciplinaRepository;
import com.syllabus.repository.HorarioRepository;
import com.syllabus.repository.TipoRepository;
import com.syllabus.repository.TurmaRepository;
import com.syllabus.repository.TurnoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CSVHelper {

	private final CursoRepository cursoRepository;
	private final DisciplinaRepository disciplinaRepository;
	private final TipoRepository tipoRepository;
	private final TurmaRepository turmaRepository;
	private final TurnoRepository turnoRepository;
	private final DiaRepository diaRepository;
	private final HorarioRepository horarioRepository;
	
	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public List<CursoModel> csvToCurso(InputStream inputStream) {
		List<CursoModel> cursos = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Nome", "Codigo", "Periodos" };

		for (CSVRecord record : records) {

			CursoModel curso = new CursoModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), record.get(headers[1]), Short.valueOf(record.get(headers[2])));
			cursos.add(curso);

		}

		return cursos;
	}

	public List<DiaModel> csvToDia(InputStream inputStream) {
		List<DiaModel> dias = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Nome", "Numero" };

		for (CSVRecord record : records) {

			DiaModel dia = new DiaModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), Short.valueOf(record.get(headers[1])));
			dias.add(dia);

		}

		return dias;
	}

	public List<DisciplinaModel> csvToDisciplina(InputStream inputStream) {
		List<DisciplinaModel> disciplinas = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Nome", "Codigo", "Carga Horaria" };

		for (CSVRecord record : records) {

			DisciplinaModel disciplina = new DisciplinaModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), record.get(headers[0]), record.get(headers[1]),
					Short.valueOf(record.get(headers[2])), null, null);

			disciplinas.add(disciplina);

		}

		return disciplinas;
	}

	public List<DisciplinaCursoModel> csvToDisciplinaCurso(InputStream inputStream) {
		List<DisciplinaCursoModel> disciplinas = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Periodo", "Curso", "Disciplina", "Tipo" };

		for (CSVRecord record : records) {

			DisciplinaCursoModel disciplinaCurso = new DisciplinaCursoModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), Short.valueOf(record.get(headers[0])),
					cursoRepository.findByCodigo(record.get(headers[1])),
					disciplinaRepository.findByCodigo(record.get(headers[2])),
					tipoRepository.findByTipoNome(record.get(headers[3])));
			disciplinas.add(disciplinaCurso);

		}

		return disciplinas;
	}

	public List<HorarioModel> csvToHorario(InputStream inputStream) {
		List<HorarioModel> horarios = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Sigla", "Faixa" };

		for (CSVRecord record : records) {

			HorarioModel horario = new HorarioModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), record.get(headers[0]), record.get(headers[1]));
			horarios.add(horario);

		}

		return horarios;
	}

	public List<HorarioAulaModel> csvToHorarioAula(InputStream inputStream) {
		List<HorarioAulaModel> horarios = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Turma", "Dia", "Horario" };

		for (CSVRecord record : records) {

			HorarioAulaModel horarioAula = new HorarioAulaModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), turmaRepository.findByCodigo(record.get(headers[0])),
					diaRepository.findByDiaNumero(Short.valueOf(record.get(headers[1]))),
					horarioRepository.findBySigla(record.get(headers[2])));
			horarios.add(horarioAula);

		}

		return horarios;
	}

	public List<TipoModel> csvToTipo(InputStream inputStream) {
		List<TipoModel> tipos = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Nome", "Valor" };

		for (CSVRecord record : records) {

			TipoModel tipo = new TipoModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), Short.valueOf(record.get(headers[1])));
			tipos.add(tipo);

		}

		return tipos;
	}

	public List<TurmaModel> csvToTurma(InputStream inputStream) {
		List<TurmaModel> turmas = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Disciplina", "Codigo", "Turno" };

		for (CSVRecord record : records) {

			TurmaModel turma = new TurmaModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), turnoRepository.findByTurnoSigla(record.get(headers[2])),
					disciplinaRepository.findByCodigo(record.get(headers[0])));
			turmas.add(turma);

		}

		return turmas;
	}

	public List<TurnoModel> csvToTurno(InputStream inputStream) {
		List<TurnoModel> turnos = new ArrayList<>();
		Iterable<CSVRecord> records = readFile(inputStream);
		String[] headers = { "Nome", "Sigla" };

		for (CSVRecord record : records) {

			TurnoModel turno = new TurnoModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
					record.get(headers[0]), record.get(headers[1]));
			turnos.add(turno);

		}

		return turnos;
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
