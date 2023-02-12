package com.syllabus.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CursoResponseModel {

	private String cursoId;
	private String codigo;
	private String nome;
	private Short periodos;
}
