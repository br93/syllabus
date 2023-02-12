package com.syllabus.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HorarioResponseModel {

	private String horarioId;
	private String sigla;
	private String faixa;
}
