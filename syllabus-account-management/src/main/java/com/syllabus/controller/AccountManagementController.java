package com.syllabus.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.data.APIResponse;
import com.syllabus.service.AccountManagementService;
import com.syllabus.util.AccountManagementMapper;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountManagementController {

    private final AccountManagementService accountManagementService;
    private final AccountManagementMapper accountManagementMapper;

    @GetMapping("user")
    public Mono<ResponseEntity<APIResponse>> hello(@RequestHeader Map<String, String> headers) {

        var user = accountManagementService.getUser(headers)
                .map(accountManagementMapper::toAPIResponse);

        return user.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    
}
