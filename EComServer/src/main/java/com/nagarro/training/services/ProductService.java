package com.nagarro.training.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nagarro.training.constants.Constants;
import com.nagarro.training.converters.DateConverter;
import com.nagarro.training.converters.ProductDtoConverter;
import com.nagarro.training.dao.ProductDao;
import com.nagarro.training.dto.ProductDto;
import com.nagarro.training.models.Product;
import com.nagarro.training.models.Serviceability;

@Service
public class ProductService {
	ObjectMapper objMapper = new ObjectMapper();

	@Autowired
	ProductDao productDao;

	@Autowired
	ProductDtoConverter pdc;

	// Method to return Product or a List of product
	public ResponseEntity<String> getProduct(Integer pid, String pname, String brand) {
		JsonNode errorNode = objMapper.createObjectNode();
		JsonNode dataNode = objMapper.createObjectNode();

		// Initiate a new ArrayList of Products
		List<Product> products = new ArrayList<>();

		// Check and Add products in ArrayList with given parameters
		if (pid != null && pname != null && brand != null) {
			products.addAll(productDao.findByPidOrPnameOrBrand(pid, pname, brand));
		} else if (pid != null && pname != null) {
			products.addAll(productDao.findByPidOrPname(pid, pname));
		} else if (pid != null && brand != null) {
			products.addAll(productDao.findByPidOrBrand(pid, brand));
		} else if (pname != null && brand != null) {
			products.addAll(productDao.findByPnameOrBrand(pname, brand));
		} else if (pid != null) {
			Product product = productDao.findById(pid).orElse(null);
			if (product != null)
				products.add(product);
		} else if (pname != null) {
			products.add(productDao.findByPname(pname));
		} else if (brand != null) {
			products.addAll(productDao.findByBrand(brand));
		} else {
			products.addAll(productDao.findAll());
		}

		// Check if products exists in ArrayList
		if (products.size() > 0) {
			// Map Product into JsonNode
			dataNode = objMapper
					.valueToTree(products.stream().map(x -> pdc.convertEntityToDto(x)).collect(Collectors.toList()));
			// Return Success Response
			return new ResponseEntity<String>(
					constructResponse(Constants.STATUS_SUCCESS, HttpStatus.OK.value(), dataNode, null), HttpStatus.OK);
		}

		// Send an error response if no product with corresponding fields exists
		((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_MESSAGE, Constants.NO_PRODUCT_FOUND);
		// Return Error Response
		return new ResponseEntity<String>(
				constructResponse(Constants.STATUS_ERROR, HttpStatus.NOT_FOUND.value(), null, errorNode),
				HttpStatus.OK);
	}

	// Method to check availability of product with corresponding pincode
	public ResponseEntity<String> getAvailability(int pid, int pincode) {
		JsonNode errorNode = objMapper.createObjectNode();
		JsonNode dataNode = objMapper.createObjectNode();
		// Get product from pid
		Product product = productDao.findById(pid).orElse(null);

		// Check if product is exists
		if (product != null) {
			// Get from product pincode
			Serviceability serviceability = product.getServiceability().stream()
					.filter(x -> (x.getPincode() == pincode)).findAny().orElse(null);

			// check if serviceability exists
			if (serviceability != null) {
				// Construct Delivery Date Response
				((ObjectNode) dataNode).put(Constants.PID_FIELD, pid);
				((ObjectNode) dataNode).put(Constants.PINCODE_FIELD, pincode);
				((ObjectNode) dataNode).put(Constants.AVAILABILITY_FIELD, true);
				((ObjectNode) dataNode).put(Constants.DELIVERY_DATE_FIELD,
						DateConverter.covertDaysIntoDate(serviceability.getExpectedDelivery()));
				// return Success Response
				return new ResponseEntity<String>(
						constructResponse(Constants.STATUS_SUCCESS, HttpStatus.OK.value(), dataNode, null),
						HttpStatus.OK);
			}

			// Not Available in given pincode
			((ObjectNode) dataNode).put(Constants.PID_FIELD, pid);
			((ObjectNode) dataNode).put(Constants.PINCODE_FIELD, pincode);
			((ObjectNode) dataNode).put(Constants.AVAILABILITY_FIELD, false);
			// return success response with availability false
			return new ResponseEntity<String>(
					constructResponse(Constants.STATUS_SUCCESS, HttpStatus.OK.value(), dataNode, null), HttpStatus.OK);
		}
		// Product with pid not found
		((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_FIELD, Constants.PID_FIELD);
		((ObjectNode) errorNode).put(Constants.RESPONSE_ERROR_MESSAGE, Constants.NO_PRODUCT_FOUND);
		// Return Error Response with Product Not Found
		return new ResponseEntity<String>(
				constructResponse(Constants.STATUS_SUCCESS, HttpStatus.NOT_FOUND.value(), null, errorNode),
				HttpStatus.OK);
	}

	// Method to add Product in database
	public String addProduct(ProductDto productDto) {
		Product product = productDao.findById(productDto.getPid()).orElse(null);

		// Product ID Does not Exists
		if (product == null) {
			try {
				return objMapper.writeValueAsString(productDao.save(pdc.convertDtoToEntity(productDto)));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "Some Error Occurred";
			}
		}

		// Return Product ID Already Exists
		return "Product Already Exists";
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
