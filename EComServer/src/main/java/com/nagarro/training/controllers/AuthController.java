package com.nagarro.training.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.training.dto.UserDto;
import com.nagarro.training.services.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
	@Autowired
	AuthService authService;

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@Valid @RequestBody UserDto userDto) {
		return authService.loginUser(userDto);
	}

	@PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> signup(@Valid @RequestBody UserDto userDto) {
		return authService.registerUser(userDto);
	}

}
