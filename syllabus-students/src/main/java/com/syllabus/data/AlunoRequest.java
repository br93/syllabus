package com.syllabus.data;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AlunoRequest {

    private Integer periodo;

    private String email;

    @JsonProperty("faculdade_sigla")
    private String siglaFaculdade;

    @JsonProperty("curso_codigo")
    private Integer codigoCurso;

    @JsonProperty("disciplinas_cursadas")
    private Set<String> codigoDisciplinas = new HashSet<>();
    
    
}
