package com.syllabus.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountManagementController {

    private final AccountManagementService accountManagementService;
    private final AccountManagementMapper accountManagementMapper;

    @GetMapping("user")
    public Mono<ResponseEntity<APIResponse>> hello(@RequestHeader Map<String, String> headers) {

    	var auth = accountManagementService.extractCookie(headers).flatMap(accountManagementService::getUser).map(accountManagementMapper::toAPIResponse);
    	return auth.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PatchMapping("user/email")
    public Mono<ResponseEntity<AccountResponse>> updateEmail(@RequestHeader Map<String, String> headers, @Valid @RequestBody AccountRequest request){
        
        var auth = accountManagementService.extractCookie(headers);
        var newUser = accountManagementService.updateEmail(auth.block(), accountManagementMapper.toAccountModel(request));
        var mappedUser = newUser.map(accountManagementMapper::toAccountResponse);
        var response = mappedUser.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().build());
        
        return response;
    }

    /*@PatchMapping("user/password")
    public ResponseEntity<AccountResponse> updatePassword(@RequestHeader Map<String, String> headers, @Valid @RequestBody AccountRequest request){

        var auth = accountManagementService.extractCookie(headers);
        var newUser = accountManagementService.updatePassword(auth, accountManagementMapper.toAccountModel(request));

        return new ResponseEntity<>(accountManagementMapper.toAccountResponse(newUser), HttpStatus.OK);
    }

    @DeleteMapping("user")
    public ResponseEntity<AccountResponse> deleteAccount(@RequestHeader Map<String, String> headers, @Valid @RequestBody AccountRequest request){
        
        var auth = accountManagementService.extractCookie(headers);
        accountManagementService.delete(auth, accountManagementMapper.toAccountModel(request));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

}
