package com.keycloak.example.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keycloak.example.config.KeycloakSecurityUtil;
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

	@Autowired
	KeycloakSecurityUtil keycloakSecurityUtil;

	@Value("${realm}")
	private String realm;

	private String role = "user-role";

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

			updateRole(keycloakId, role);
		}
		// Fetch all roles for given user
		List<RoleRepresentation> roleRepresentations = getRoles(keycloakId);
		// check if User having user-role if not assign to logged In user
		if (roleRepresentations.stream().filter(i -> i.getName().equalsIgnoreCase(role)).collect(Collectors.toList())
				.size() == 0) {
			updateRole(keycloakId, role);
		}
		return new ResponseDTO().message("Success").object(user).status(StatusEnum.SUCCESS);
	}

	private void updateRole(String keycloakId, String newlyAddedRole) {
		Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
		RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(newlyAddedRole).toRepresentation();
		keycloak.realm(realm).users().get(keycloakId).roles().realmLevel().add(Arrays.asList(roleRepresentation));
		getRoles(keycloakId);
	}

	private List<RoleRepresentation> getRoles(String keycloakId) {
		Keycloak keycloak = keycloakSecurityUtil.getKeycloakInstance();
		return keycloak.realm(realm).users().get(keycloakId).roles().realmLevel().listAll();

	}
}
