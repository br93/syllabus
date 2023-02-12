package com.syllabus.model;

import java.time.Instant;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_disciplina")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DisciplinaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant updatedAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant deletedAt;

	private String disciplinaId;
	private String nome;
	private String codigo;
	private Short cargaHoraria;

	@ManyToMany
	@JoinTable(name = "disciplinas_equivalentes", joinColumns = @JoinColumn(name = "disciplina_id"), inverseJoinColumns = @JoinColumn(name = "equivalente_id"))
	private Set<DisciplinaModel> equivalentes;

	@ManyToMany
	@JoinTable(name = "disciplinas_prerequisitos", joinColumns = @JoinColumn(name = "disciplina_id"), inverseJoinColumns = @JoinColumn(name = "pre_requisito_id"))
	private Set<DisciplinaModel> preRequisitos;

}
