package com.syllabus.util;

import org.springframework.stereotype.Service;

import com.syllabus.client.account.AccountClient;
import com.syllabus.client.account.response.AccountResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserValidation {

    private final AccountClient accountClient;

    public AccountResponse getUser() {
        return this.accountClient.getAccount().getUser();
    }

    public boolean isAuthorizedById(String id) {
        return this.getUser().getUserId().equals(id);

    }

}
