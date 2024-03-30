package com.keycloak.example.controller;

import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.keycloak.example.model.User;
import com.keycloak.example.services.UserService;

@Service
public abstract class AbstractController {

	@Autowired
	UserService userService;

	public User getLoggedInUser(Jwt jwt) {
		String keycloakId = jwt.getClaimAsString(AccessToken.SUBJECT);
		return userService.findByKeycloakId(keycloakId);
	}
}
