package com.nagarro.training.dto;

public class ServiceabilityDto {
	
	private int pincode;

	private String expectedDelivery;

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getExpectedDelivery() {
		return expectedDelivery;
	}

	public void setExpectedDelivery(String expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}
}
