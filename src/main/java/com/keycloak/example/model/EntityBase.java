package com.keycloak.example.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class EntityBase implements Serializable {

	private static final long serialVersionUID = 2187547867472438846L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Long createdBy;

	private LocalDateTime createdDate;

	private Long updatedBy;

	private LocalDateTime updatedDate;

}
