package com.keycloak.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keycloak.example.dto.PersonDTO;
import com.keycloak.example.dto.ResponseDTO;
import com.keycloak.example.model.User;
import com.keycloak.example.services.PersonService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/person")
@SecurityRequirement(name = "Keycloak")
public class PersonController extends AbstractController {

	@Autowired
	PersonService personService;

	@GetMapping(value = "/{id}")
	public ResponseDTO getAllUsers(@PathVariable Long id) {
		return personService.getById(id);
	}

	@GetMapping(value = "/personByName/{name}")
	public ResponseDTO getPersoneByName(@PathVariable String name) {
		return personService.findByName(name);
	}

	@GetMapping(value = "/all")
	public ResponseDTO getAll() {
		return personService.getAllPersons();
	}

	@DeleteMapping(value = "/{id}")
	public HttpStatus deletePerson(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
		User user = getLoggedInUser(jwt);
		personService.deletePerson(id, user);
		return HttpStatus.NO_CONTENT;
	}

	@PostMapping
	public ResponseDTO insertPersone(@RequestBody PersonDTO person, @AuthenticationPrincipal Jwt jwt) {
		User user = getLoggedInUser(jwt);
		return personService.addPerson(person, user);
	}

	@PutMapping
	public ResponseDTO updatePerson(@RequestBody PersonDTO personDto, @AuthenticationPrincipal Jwt jwt) {
		User user = getLoggedInUser(jwt);
		return personService.updatePerson(personDto, user);
	}
}
