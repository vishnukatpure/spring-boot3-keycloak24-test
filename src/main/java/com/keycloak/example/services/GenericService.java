package com.keycloak.example.services;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.keycloak.example.dto.ResponseDTO;
import com.keycloak.example.enums.StatusEnum;
import com.keycloak.example.model.EntityBase;
import com.keycloak.example.model.User;

@Service
public abstract class GenericService {

	private static final ModelMapper mapper = new ModelMapper();

	public ResponseDTO bindResponse(Object dto) {
		return new ResponseDTO().message("Success").object(dto).status(StatusEnum.SUCCESS);
	}

	public ModelMapper getMapper() {
		return mapper;
	}

	public void setUserDetails(EntityBase entityBase, User user) {
		entityBase.setUpdatedBy(user.getId());
		entityBase.setUpdatedDate(LocalDateTime.now());
	}

	public void setUserDetailsForUpdate(EntityBase entityBase, User user) {
		entityBase.setCreateBy(user.getId());
		entityBase.setCreateDate(LocalDateTime.now());
	}
}