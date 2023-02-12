package com.syllabus.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisciplinaResponseModel {

	private String disciplinaId;
	private String codigo;
	private String nome;
	private Short cargaHoraria;
}
