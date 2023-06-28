package com.syllabus.data.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "alunos")
public class AlunoModel implements Serializable{

    @MongoId(FieldType.OBJECT_ID)
    private String alunoId;

    @Indexed(unique = true)
    private String email;

    private Integer periodo;

    @Field(name = "faculdade")
    private String siglaFaculdade;

    @Field(name = "curso")
    private Integer codigoCurso;

    @Field(name = "disciplinasCursadas")
    private Set<String> codigoDisciplinas = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant createdAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant updatedAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant deletedAt;
    
}
