package com.keycloak.example.model;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.StringUtils;

import com.keycloak.example.exception.FormValidationException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(appliesTo = "person")
@EnableJpaAuditing
@Getter
@Setter
@ToString
public class Person extends EntityBase {

	private static final long serialVersionUID = 1676382221207795923L;
	@Column(name = "age")
	private Integer age;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;

	public void validate() {
		String msg = "";
		if (!StringUtils.hasLength(firstName))
			msg += "invalid First name";

		if (StringUtils.hasLength(msg))
			throw new FormValidationException(msg);
	}
}
