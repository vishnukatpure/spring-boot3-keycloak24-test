package com.keycloak.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.keycloak.example.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	List<User> findByFirstName(String firstName);

	User findByKeycloakId(String keycloakId);
}
