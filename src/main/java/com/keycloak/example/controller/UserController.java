package com.keycloak.example.controller;

import java.time.LocalDateTime;

import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keycloak.example.dto.ResponseDTO;
import com.keycloak.example.enums.StatusEnum;
import com.keycloak.example.model.User;
import com.keycloak.example.services.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/user")
@SecurityRequirement(name = "Keycloak")
public class UserController extends AbstractController {

	@Autowired
	UserService userService;

	@PostMapping
	public ResponseDTO saveUser(@AuthenticationPrincipal Jwt jwt) {
		String username = jwt.getClaimAsString(AccessToken.PREFERRED_USERNAME);
		String email = jwt.getClaimAsString(AccessToken.EMAIL);
		String keycloakId = jwt.getClaimAsString(AccessToken.SUBJECT);
		String firstname = jwt.getClaimAsString(AccessToken.NAME);
		String lastname = jwt.getClaimAsString(AccessToken.FAMILY_NAME);
		User user = userService.findByKeycloakId(keycloakId);
		if (user == null) {
			user = new User();
			user.setUserName(username);
			user.setEmail(email);
			user.setKeycloakId(keycloakId);
			user.setFirstName(firstname);
			user.setLastName(lastname);
			user.setCreatedDate(LocalDateTime.now());
			user = userService.addUser(user);
		}
		return new ResponseDTO().message("Success").object(user).status(StatusEnum.SUCCESS);
	}
}
