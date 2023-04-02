package com.syllabus.service;

import java.time.Instant;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.syllabus.client.AuthClient;
import com.syllabus.client.ClientResponse;
import com.syllabus.data.model.AccountModel;
import com.syllabus.exception.EmailOrPasswordException;
import com.syllabus.exception.UserNotAuthorizedException;
import com.syllabus.repository.AccountManagementRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AccountManagementService {

	private final AuthClient authClient;
	private final AccountManagementRepository accountManagementRepository;
	private final PasswordEncoder passwordEncoder;

	private final Environment environment;
	private static final String ERROR = "account.management.error";

	public final Logger logger = LoggerFactory.getLogger(AccountManagementService.class);

	public Mono<String> extractCookie(Map<String, String> headers) {
		var values = Flux.fromIterable(headers.values());

		return values.filter(x -> x.contains("Authorization"))
				.next()
				.switchIfEmpty(Mono.error(new UserNotAuthorizedException("User not authorized")));
	}

	public Mono<ClientResponse> getUser(Map<String, String> authorization) {
		return this.extractCookie(authorization).flatMap(authClient::getMe);

	}

	public Mono<AccountModel> getUserByUserId(String userId) {
		return accountManagementRepository.findByUserId(userId);
	}

	public Mono<AccountModel> updateEmail(Map<String, String> authorization, AccountModel request) {

		return Mono.just(authorization)
				.flatMap(this::getUser)
				.map(auth -> auth.getUser().getUserId())
				.flatMap(this::getUserByUserId)
                .map(user-> {
                    if (emailIsEqual(request.getEmail(), user.getEmail()) || !passwordIsEqual(request.getPassword(), user.getPassword()))
                        throw new EmailOrPasswordException(environment.getProperty(ERROR));
                    return user;
                })
				.flatMap(user -> {
					user.setEmail(request.getEmail());
					user.setUpdatedAt(Instant.now());
					return this.accountManagementRepository.save(user);
				});
	}

	public Mono<AccountModel> updatePassword(Map<String, String> authorization, AccountModel request) {

		return Mono.just(authorization)
				.flatMap(this::getUser)
				.map(auth -> auth.getUser().getUserId())
				.flatMap(this::getUserByUserId)
                .map(user -> {
                    if(!emailIsEqual(request.getEmail(), user.getEmail()) || passwordIsEqual(request.getPassword(), user.getPassword()))
                        throw new EmailOrPasswordException(environment.getProperty(ERROR));
                    return user;
                })
				.flatMap(user -> {
					user.setPassword(passwordEncoder.encode(request.getPassword()));
					user.setUpdatedAt(Instant.now());
					return this.accountManagementRepository.save(user);
				});

	}

	public Mono<Void> deleteAccount(Map<String, String> authorization, AccountModel request) {

		return Mono.just(authorization).flatMap(this::getUser).map(auth -> auth.getUser().getUserId())
				.flatMap(this::getUserByUserId)
				.map(user -> {
                    if(!emailIsEqual(request.getEmail(), user.getEmail()) || !passwordIsEqual(request.getPassword(), user.getPassword()))
                        throw new EmailOrPasswordException(environment.getProperty(ERROR));
                    return user;
                })
				.flatMap(user -> {
					user.setUpdatedAt(Instant.now());
					user.setDeletedAt(Instant.now());
					return this.accountManagementRepository.save(user);
				}).then();

	}

    private Boolean emailIsEqual(String newEmail, String oldEmail){
        return oldEmail.equals(newEmail);
    }

    private Boolean passwordIsEqual(String newPassword, String oldPassword){
        return passwordEncoder.matches(newPassword, oldPassword);
    }

	
}
