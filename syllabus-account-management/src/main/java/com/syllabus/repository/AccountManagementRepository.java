package com.syllabus.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.syllabus.data.model.AccountModel;

import reactor.core.publisher.Mono;

@Repository
public interface AccountManagementRepository extends ReactiveCrudRepository<AccountModel, Long> {
    
    Mono<AccountModel> findByUserId(String userId);
}
