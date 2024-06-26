package com.keycloak.example.dto;

import com.keycloak.example.enums.StatusEnum;

public class ResponseDTO {

	private StatusEnum status;
	private String message;
	private Object object;

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public ResponseDTO message(String message) {
		this.message = message;
		return this;
	}

	public ResponseDTO object(Object object) {
		this.object = object;
		return this;
	}

	public ResponseDTO status(StatusEnum status) {
		this.status = status;
		return this;
	}

}
