package com.syllabus.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.model.APIResponse;
import com.syllabus.service.AccountManagementService;
import com.syllabus.util.AccountManagementMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountManagementController {

    private final AccountManagementService accountManagementService;
    private final AccountManagementMapper accountManagementMapper;

    @GetMapping("user")
    public ResponseEntity<APIResponse> hello(@RequestHeader Map<String, String> headers) {

        var auth = accountManagementService.extractCookie(headers);
        var user = accountManagementService.getUser(auth);

        return new ResponseEntity<>(accountManagementMapper.toAPIResponse(user), HttpStatus.OK);
    }

}
