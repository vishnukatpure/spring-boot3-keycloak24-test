package com.keycloak.example.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keycloak.example.dto.PersonDTO;
import com.keycloak.example.dto.ResponseDTO;
import com.keycloak.example.model.Person;
import com.keycloak.example.model.User;
import com.keycloak.example.repository.PersonRepository;

@Service
public class PersonService extends GenericService {

	@Autowired
	PersonRepository personRepository;

	@Transactional
	public ResponseDTO getAllPersons() {
		List<PersonDTO> dto = new ArrayList<>();
		personRepository.findAll().forEach(ob -> dto.add(getMapper().map(ob, PersonDTO.class)));

		return bindResponse(dto);
	}

	@Transactional
	public ResponseDTO findByName(String name) {
		List<PersonDTO> dto = new ArrayList<>();
		personRepository.findByFirstName(name).forEach(ob -> dto.add(getMapper().map(ob, PersonDTO.class)));
		return bindResponse(dto);
	}

	@Transactional
	public ResponseDTO getById(Long id) {
		return bindResponse(getMapper().map(personRepository.findById(id).get(), PersonDTO.class));
	}

	@Transactional
	public void deletePerson(Long personId, User user) {
		personRepository.deleteById(personId);
	}

	@Transactional
	public ResponseDTO addPerson(PersonDTO personDto, User user) {
		setUserDetails(user, user);
		Person person = getMapper().map(personDto, Person.class);
		person.validate();
		setUserDetails(person, user);
		return bindResponse(getMapper().map(personRepository.save(person), PersonDTO.class));
	}

	@Transactional
	public ResponseDTO updatePerson(PersonDTO personDto, User user) {
		Person person = getMapper().map(personDto, Person.class);
		setUserDetailsForUpdate(person, user);
		return bindResponse(getMapper().map(personRepository.save(person), PersonDTO.class));
	}

}
