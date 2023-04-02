package com.syllabus.service;

import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.syllabus.client.AuthClient;
import com.syllabus.client.ClientResponse;
import com.syllabus.data.model.AccountModel;
import com.syllabus.exception.EmailOrPasswordException;
import com.syllabus.exception.UserNotAuthorizedException;
import com.syllabus.exception.UserNotFoundException;
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

		return accountManagementRepository.findByUserId(userId)
			.switchIfEmpty(Mono.error(new UserNotFoundException("User not found", new Throwable("userId"))));

	}

	public Mono<AccountModel> updateEmail(Map<String, String> authorization, AccountModel request) {

		return Mono.just(authorization)
			.flatMap(this::getUser)
			.map(auth -> auth.getUser().getUserId())
			.flatMap(this::getUserByUserId)
			.doOnNext(x -> {
				this.validateDifferentEmail(request.getEmail(), x.getEmail());
				this.validateSamePassword(request.getPassword(), x.getPassword());
			})
			.flatMap(u -> {
				u.setEmail(request.getEmail());
				u.setPassword(request.getPassword());
				return this.accountManagementRepository.save(u);
			});
	}


	public AccountModel updatePassword(String authorization, AccountModel request) {
		/*
		 * var userId = this.getUser(authorization).getUser().getUserId();
		 * var user = this.getUserByUserId(userId);
		 * 
		 * validateSameEmail(request.getEmail(), user.getEmail());
		 * validateDifferentPassword(request.getPassword(), user.getPassword());
		 * 
		 * AccountModel updatedUser = user;
		 * updatedUser.setPassword(passwordEncoder.encode(request.getPassword()));
		 * updatedUser.setUpdatedAt(Instant.now());
		 * 
		 * return accountManagementRepository.save(updatedUser);
		 */
		return null;
	}

	public void delete(String authorization, AccountModel request) {
		/*
		 * var userId = this.getUser(authorization).getUser().getUserId();
		 * var user = this.getUserByUserId(userId);
		 * 
		 * validateSameEmail(request.getEmail(), user.getEmail());
		 * validateSamePassword(request.getPassword(), user.getPassword());
		 * 
		 * user.setUpdatedAt(Instant.now());
		 * user.setDeletedAt(Instant.now());
		 * 
		 * accountManagementRepository.save(user);
		 */
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
