package com.keycloak.example.model;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(appliesTo = "user")
@EnableJpaAuditing
@Getter
@Setter
public class User extends EntityBase {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "keycloak_id")
	private String keycloakId;
}
