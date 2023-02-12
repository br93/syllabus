package com.syllabus.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.syllabus.shared.CursoResponseModel;
import com.syllabus.shared.DiaResponseModel;
import com.syllabus.shared.DisciplinaResponseModel;
import com.syllabus.shared.HorarioResponseModel;
import com.syllabus.shared.TipoResponseModel;
import com.syllabus.shared.TurmaResponseModel;
import com.syllabus.shared.TurnoResponseModel;

@FeignClient(name="settings-ms")
public interface SettingsClient {
	
	@GetMapping("/api/v1/config/cursos/{curso_id}")
	public CursoResponseModel getCurso(@PathVariable(name = "curso_id") String cursoId);
	
	@GetMapping("/api/v1/config/dias/{dia_id}")
	public DiaResponseModel getDia(@PathVariable(name = "dia_id") String diaId);
	
	@GetMapping("/api/v1/config/disciplinas/{disciplina_id}")
	public DisciplinaResponseModel getDisciplina(@PathVariable(name = "disciplina_id") String disciplinaId);

	@GetMapping("/api/v1/config/horarios/{horario_id}")
	public HorarioResponseModel getHorario(@PathVariable(name = "horario_id") String horarioId);
	
	@GetMapping("/api/v1/config/tipos/{tipo_id}")
	public TipoResponseModel getTipo(@PathVariable(name = "tipo_id") String tipoId);
	
	@GetMapping("/api/v1/config/turmas/{turma_id}")
	public TurmaResponseModel getTurma(@PathVariable(name = "turma_id") String turmaId);
	
	@GetMapping("/api/v1/config/turnos/{turno_id}")
	public TurnoResponseModel getTurno(@PathVariable(name = "turno_id") String turnoId);
	
}
