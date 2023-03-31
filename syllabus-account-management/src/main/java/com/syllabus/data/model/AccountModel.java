package com.syllabus.data.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
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
