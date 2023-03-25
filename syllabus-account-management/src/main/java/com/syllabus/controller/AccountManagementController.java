package com.syllabus.controller;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountManagementController {

    @GetMapping("hello")
    public ResponseEntity<String> hello(@RequestHeader Map<String, String> headers) {

        var values = headers.values();
        var user = values.stream().filter(x -> x.contains("User")).findFirst().orElseThrow(RuntimeException::new);
        var index = user.indexOf("User");
        
        return new ResponseEntity<>("Hello user " + user.substring(index+5, user.length()), HttpStatus.OK);
    }

}
