package com.syllabus.data.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("tb_usuario")
public class AccountModel {

	@Id
	private Long id;

	private Instant createdAt;
	private Instant updatedAt;
	private Instant deletedAt;

	private String userId;
	private String email;
	private String password;

}
