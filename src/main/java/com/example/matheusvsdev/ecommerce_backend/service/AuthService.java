package com.example.matheusvsdev.ecommerce_backend.service;

import com.example.matheusvsdev.ecommerce_backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserService userService;
	
	public void validateSelfOrAdmin(Long userId) {
		User me = userService.autenthicated();
		if (me.hasRole("ROLE_ADMIN")) {
			return;
		}
		if (!me.getId().equals(userId)) {
			throw new RuntimeException("Access denied. Should be self of admin");
		}
	}
}
