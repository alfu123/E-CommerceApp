package com.nagarro.training.dto;

import java.util.ArrayList;
import java.util.List;


public class ProductDto {
	private int pid;

	private String pname;

	private String brand;

	private String description;

	private double price;

	private String imageUrl;

	private List<ServiceabilityDto> serviceability = new ArrayList<>();

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<ServiceabilityDto> getServiceability() {
		return serviceability;
	}

	public void setServiceability(List<ServiceabilityDto> serviceability) {
		this.serviceability = serviceability;
	}
	
	
}
