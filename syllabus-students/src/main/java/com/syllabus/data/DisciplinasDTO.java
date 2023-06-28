package com.syllabus.data;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisciplinasDTO {

    @JsonProperty("codigos")
    private Set<String> codigoDisciplinas = new HashSet<>();
    
}
