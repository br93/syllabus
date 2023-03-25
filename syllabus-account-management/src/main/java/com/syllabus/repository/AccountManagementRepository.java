package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.data.model.AccountModel;

public interface AccountManagementRepository extends JpaRepository<AccountModel, Long> {
    
    Optional<AccountModel> findByUserId(String userId);
}
