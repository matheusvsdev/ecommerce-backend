package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.dto.EmailDTO;
import com.example.matheusvsdev.ecommerce_backend.entities.PasswordRecover;
import com.example.matheusvsdev.ecommerce_backend.entities.User;
import com.example.matheusvsdev.ecommerce_backend.repository.PasswordRecoverRepository;
import com.example.matheusvsdev.ecommerce_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class AuthService {

	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;

	@Value("${email.password-recover.uri}")
	private String recoverUri;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordRecoverRepository passwordRecoverRepository;

	@Autowired
	private UserService userService;

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

	public void createRecoverToken(EmailDTO body) {
		User user = userRepository.findByEmail(body.getEmail());
		if (user == null) {
			throw new RuntimeException("Email não encontrado");
		}

		String token = UUID.randomUUID().toString();

		PasswordRecover passwordRecover = new PasswordRecover();
		passwordRecover.setEmail(body.getEmail());
		passwordRecover.setToken(token);
		passwordRecover.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));
		passwordRecover = passwordRecoverRepository.save(passwordRecover);

		String text = "Acesse o link para definir uma nova senha\n\n"
				+ recoverUri + token + ". Validade de " + tokenMinutes + " minutos";

		emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);
	}
}
