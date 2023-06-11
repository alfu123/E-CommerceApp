package com.nagarro.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.training.dto.ProductDto;
import com.nagarro.training.services.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getProduct(@RequestParam(required = false) Integer pid,
			@RequestParam(required = false) String pname, @RequestParam(required = false) String brand) {

		return productService.getProduct(pid, pname, brand);
	}

	@GetMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkAvailability(@RequestParam("pid") int pid,
			@RequestParam("pincode") int pincode) {
		return productService.getAvailability(pid, pincode);
	}

	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public String addProduct(@RequestBody ProductDto productDto) {

		return productService.addProduct(productDto);
	}
}
