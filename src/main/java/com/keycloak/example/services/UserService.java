package com.keycloak.example.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keycloak.example.model.User;
import com.keycloak.example.repository.UserRepository;

@Service
public class UserService extends GenericService {

	@Autowired
	UserRepository userRepository;

	@Transactional
	public User getById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.isPresent() ? user.get() : null;
	}

	@Transactional
	public void deletePerson(Long personId) {
		userRepository.deleteById(personId);
	}

	@Transactional
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Transactional
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	public User findByKeycloakId(String keycloakId) {
		return userRepository.findByKeycloakId(keycloakId);
	}

}
