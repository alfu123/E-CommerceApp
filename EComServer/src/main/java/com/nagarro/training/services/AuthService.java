package com.nagarro.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nagarro.training.constants.Constants;
import com.nagarro.training.converters.UserDtoConverter;
import com.nagarro.training.dao.UserDao;
import com.nagarro.training.dto.UserDto;
import com.nagarro.training.models.User;

@Service
public class AuthService {
	ObjectMapper objMapper = new ObjectMapper();
	JsonNode errorNode = objMapper.createObjectNode();
	JsonNode dataNode = objMapper.createObjectNode();

	@Autowired
	UserDao userDao;

	@Autowired
	UserDtoConverter udc;

	// Login User Method
	public ResponseEntity<String> loginUser(UserDto userDto) {
		User user = userDao.findByUsername(userDto.getUsername());
		
		if (user != null) {
			// Username Exists
			// Check password
			if (user.getPassword().equals(userDto.getPassword())) {
				// Convert User to JsonNode
				dataNode = objMapper.valueToTree(udc.convertEntityToDto(user));
				// Remove password field from JSON Node
				((ObjectNode) dataNode).remove("password");

				// Successful Login Response
				return new ResponseEntity<String>(
						constructResponse(Constants.STATUS_SUCCESS, HttpStatus.OK.value(), dataNode, null),
						HttpStatus.OK);
			}
			// Modify Error Node for error response
			((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_FIELD, Constants.PASSWORD_FIELD);
			((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_MESSAGE, Constants.INCORRECT_PASSWORD);

			// Incorrect Password Response
			return new ResponseEntity<String>(
					constructResponse(Constants.STATUS_ERROR, HttpStatus.UNAUTHORIZED.value(), null, errorNode),
					HttpStatus.OK);
		}
		// Modify json Node for response
		((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_FIELD, Constants.USERNAME_FIELD);
		((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_MESSAGE, Constants.USERNAME_NOT_EXISTS);

		// Username does not Exists Response
		return new ResponseEntity<String>(
				constructResponse(Constants.STATUS_ERROR, HttpStatus.UNAUTHORIZED.value(), null, errorNode),
				HttpStatus.OK);
	}

	// SignUp User Method
	public ResponseEntity<String> registerUser(UserDto userDto) {
		// get User by username
		User user = userDao.findByUsername(userDto.getUsername());

		// Check if user with username does not exists in database
		if (user == null) {
			user = userDao.save(udc.convertDtoToEntity(userDto));

			// If User is saved Successfully
			if (user != null) {
				// delete password field from JSON response
				dataNode = objMapper.valueToTree(udc.convertEntityToDto(user));
				((ObjectNode) dataNode).remove("password");

				// Return if user is created is successfully
				return new ResponseEntity<String>(
						constructResponse(Constants.STATUS_SUCCESS, HttpStatus.CREATED.value(), dataNode, null),
						HttpStatus.CREATED);
			}
			// Modify JsonNode for response
			((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.name());
			// Return Internal Server error response
			return new ResponseEntity<String>(constructResponse(Constants.STATUS_ERROR,
					HttpStatus.INTERNAL_SERVER_ERROR.value(), null, errorNode), HttpStatus.OK);
		}
		// Modify JsonNode for response
		((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_FIELD, Constants.USERNAME_FIELD);
		((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_MESSAGE, Constants.USERNAME_ALREADY_EXISTS);

		// Return Username Already Exists Response
		return new ResponseEntity<String>(
				constructResponse(Constants.STATUS_ERROR, HttpStatus.CONFLICT.value(), null, errorNode),
				HttpStatus.OK);
	}

	// Method to construct response
	public String constructResponse(String status, int statusCode, JsonNode dataNode, JsonNode errorNode) {
		String response = "";

		// Create a new Empty Object Node
		ObjectNode objNode = objMapper.createObjectNode();
		objNode.put(Constants.RESPONSE_STATUS, status);
		objNode.put(Constants.RESPONSE_STATUS_CODE, statusCode);
		objNode.set(Constants.RESPONSE_DATA_STRING, dataNode);
		objNode.set(Constants.RESPONSE_ERRORS, errorNode);

		try {
			response = objMapper.writeValueAsString(objNode);
		} catch (JsonProcessingException e) {
			response = null;
		}

		return response;
	}
}
