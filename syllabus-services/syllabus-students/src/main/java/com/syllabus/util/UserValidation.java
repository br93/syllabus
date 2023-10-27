package com.syllabus.util;

import org.springframework.stereotype.Service;

import com.syllabus.client.account.AccountClient;
import com.syllabus.client.account.response.AccountResponse;
import com.syllabus.exception.CustomCallNotPermittedException;
import com.syllabus.exception.UserUnauthorizedException;
import com.syllabus.message.MessageConstants;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserValidation {

    private final AccountClient accountClient;

    @CircuitBreaker(name = "get-user", fallbackMethod = "fallbackGetUser")
    public AccountResponse getUser() {
        return accountClient.getAccount().getUser();
    }

    public AccountResponse fallbackGetUser(Throwable exception) {
        throw new CustomCallNotPermittedException(MessageConstants.SERVICE_UNAVAILABLE);
    }


    public boolean isAuthorizedById(String id) {
        return this.getUser().getUserId().equals(id);

    }

}
