package com.syllabus.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TurmaResponseModel {

	private String turmaId;
	private String codigo;
	private String turno;
}
