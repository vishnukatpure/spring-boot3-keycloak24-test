package com.keycloak.example.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.keycloak.example.dto.ResponseDTO;
import com.keycloak.example.enums.StatusEnum;

@Service
public abstract class GenericService {

	private static final ModelMapper mapper = new ModelMapper();

	public ResponseDTO bindResponse(Object dto) {
		return new ResponseDTO().message("Success").object(dto).status(StatusEnum.SUCCESS);
	}

	public ModelMapper getMapper() {
		return mapper;
	}

}