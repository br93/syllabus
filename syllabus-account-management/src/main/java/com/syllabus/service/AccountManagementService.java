package com.syllabus.service;

import java.time.Instant;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.syllabus.client.AuthClient;
import com.syllabus.client.ClientResponse;
import com.syllabus.data.model.AccountModel;
import com.syllabus.exception.EmailOrPasswordException;
import com.syllabus.exception.UserNotFoundException;
import com.syllabus.repository.AccountManagementRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountManagementService {

    private final AuthClient authClient;
    private final AccountManagementRepository accountManagementRepository;
    private final PasswordEncoder passwordEncoder;

    private final Environment environment;
    private static final String ERROR = "account.management.error";

    public String extractCookie(Map<String, String> headers) {
        var values = headers.values();

        return values.stream().filter(x -> x.contains("Authorization")).findFirst().orElseThrow(RuntimeException::new);
    }

    public ClientResponse getUser(String authorization) {
        return authClient.getMe(authorization);
    }

    public AccountModel getUserByUserId(String userId) {
        return accountManagementRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found", new Throwable("userId")));
    }

    public AccountModel updateEmail(String authorization, AccountModel request) {
        var userId = this.getUser(authorization).getUser().getUserId();
        var user = this.getUserByUserId(userId);

        validateDifferentEmail(request.getEmail(), user.getEmail());
        validateSamePassword(request.getPassword(), user.getPassword());

        AccountModel updatedUser = user;
        updatedUser.setEmail(request.getEmail());
        updatedUser.setUpdatedAt(Instant.now());

        return accountManagementRepository.save(updatedUser);
    }

    public AccountModel updatePassword(String authorization, AccountModel request) {
        var userId = this.getUser(authorization).getUser().getUserId();
        var user = this.getUserByUserId(userId);

        validateSameEmail(request.getEmail(), user.getEmail());
        validateDifferentPassword(request.getPassword(), user.getPassword());
       
        AccountModel updatedUser = user;
        updatedUser.setPassword(passwordEncoder.encode(request.getPassword()));
        updatedUser.setUpdatedAt(Instant.now());

        return accountManagementRepository.save(updatedUser);
    }

    public void delete(String authorization, AccountModel request) {
        var userId = this.getUser(authorization).getUser().getUserId();
        var user = this.getUserByUserId(userId);

        validateSameEmail(request.getEmail(), user.getEmail());
        validateSamePassword(request.getPassword(), user.getPassword());

        user.setUpdatedAt(Instant.now());
        user.setDeletedAt(Instant.now());

        accountManagementRepository.save(user);
    }

    private void validateDifferentEmail(String newEmail, String oldEmail){
        if (oldEmail.equals(newEmail))
            throw new EmailOrPasswordException(environment.getProperty(ERROR));
    }

    private void validateSameEmail(String newEmail, String oldEmail) {
        if (!oldEmail.equals(newEmail))
            throw new EmailOrPasswordException(environment.getProperty(ERROR));
    }

    private void validateDifferentPassword(String newPassword, String oldPassword) {
        if (passwordEncoder.matches(newPassword, oldPassword))
            throw new EmailOrPasswordException(environment.getProperty(ERROR));
    }  

    private void validateSamePassword(String newPassword, String oldPassword) {
        if (!passwordEncoder.matches(newPassword, oldPassword))
            throw new EmailOrPasswordException(environment.getProperty(ERROR));
    }

}
