package com.example.demo.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class CreateUserRequest {

	@JsonProperty
	////used to map property names with JSON keys during serialization and deserialization
	@NotNull(message = "Password should not be empty")
	private String username;

	@JsonProperty
	////used to map property names with JSON keys during serialization and deserialization
	@NotNull(message = "Password should not be empty")
	//@NotNull annotation is, actually, an explicit contract declaring that
	// a method should not return null. Variables (fields, local variables, and parameters) cannot hold a null value
	public String password;

	@JsonProperty
	//used to map property names with JSON keys during serialization and deserialization
	@NotNull(message = "Password confirmation should not be empty")
	//@NotNull annotation is, actually, an explicit contract declaring that
	// a method should not return null. Variables (fields, local variables, and parameters) cannot hold a null value
	public String confirmPassword;

	public String getPassword() {
		String password;
		password = this.password;
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		String confirmPassword;
		confirmPassword = this.confirmPassword;
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUsername() {
		String username1;
		username1 = username;
		return username1;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
