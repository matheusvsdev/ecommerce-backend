package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	protected User autenthicated() {
		Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwt = (Jwt) authenticator.getPrincipal();
		String username = jwt.getClaim("username");
		return userRepository.findByEmail(username);
	}

	public void validateSelfOrAdmin(Long userId) {
		User me = autenthicated();
		if (me.hasRole("ROLE_ADMIN")) {
			return;
		}
		if (!me.getId().equals(userId)) {
			throw new RuntimeException("Access denied. Should be self of admin");
		}
	}
}
