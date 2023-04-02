package com.syllabus.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.data.APIResponse;
import com.syllabus.data.AccountRequest;
import com.syllabus.data.AccountResponse;
import com.syllabus.service.AccountManagementService;
import com.syllabus.util.AccountManagementMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/account/user")
@RequiredArgsConstructor
public class AccountManagementController {

    private final AccountManagementService accountManagementService;
    private final AccountManagementMapper accountManagementMapper;

    @GetMapping
    public Mono<ResponseEntity<APIResponse>> hello(@RequestHeader Map<String, String> headers) {

        var user = accountManagementService.getUser(headers)
                .map(accountManagementMapper::toAPIResponse);

        return user.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<AccountResponse>> getUserByUserId(@PathVariable String id) {

        var user = accountManagementService.getUserByUserId(id).map(accountManagementMapper::toAccountResponse);

        return user.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("email")
    public Mono<ResponseEntity<AccountResponse>> updateEmail(@RequestHeader Map<String, String> headers,
            @Valid @RequestBody Mono<AccountRequest> request) {

        var user = request.map(accountManagementMapper::toAccountModel)
                .flatMap(model -> accountManagementService.updateEmail(headers, model))
                .map(accountManagementMapper::toAccountResponse);

        return user.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PatchMapping("password")
    public Mono<ResponseEntity<AccountResponse>> updatePassword(@RequestHeader Map<String, String> headers,
            @Valid @RequestBody Mono<AccountRequest> request) {

        var user = request.map(accountManagementMapper::toAccountModel).flatMap(model -> accountManagementService
                .updatePassword(headers, model).map(accountManagementMapper::toAccountResponse));

        return user.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteAccount(@RequestHeader Map<String, String> headers,
            @Valid @RequestBody Mono<AccountRequest> request) {

        var user = request.map(accountManagementMapper::toAccountModel)
                .flatMap(model -> accountManagementService.deleteAccount(headers, model));

        return user.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
