package com.example.matheusvsdev.ecommerce_backend.tests;

import com.example.matheusvsdev.ecommerce_backend.projection.UserDetailsProjection;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsFactory {
	
	public static List<UserDetailsProjection> createCustomClientUser(String username) {
		
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "password123", 1L, "ROLE_CLIENT", true));
		return list;
	}

	public static List<UserDetailsProjection> createCustomAdminUser(String username) {
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "password123", 2L, "ROLE_ADMIN", true));
		return list;
	}
	
	public static List<UserDetailsProjection> createCustomAdminClientUser(String username) {
		
		List<UserDetailsProjection> list = new ArrayList<>();
		list.add(new UserDetailsImpl(username, "123", 1L, "ROLE_CLIENT", true));
		list.add(new UserDetailsImpl(username, "123", 2L, "ROLE_ADMIN", true));
		return list;
	}

}

class UserDetailsImpl implements UserDetailsProjection {
	
	private String username;
	private String password;
	private Long roleId;
	private String authority;
	private Boolean active;
	
	public UserDetailsImpl() {
	}

	public UserDetailsImpl(String username, String password, Long roleId, String authority, Boolean active) {
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.authority = authority;
		this.active = active;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Long getRoleId() {
		return roleId;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	@Override
	public Boolean getActive() {
		return active;
	}
}
