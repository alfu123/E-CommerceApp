package com.nagarro.training.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "serviceability")
public class Serviceability {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int sid;

	@Column(name = "pincode")
	private int pincode;

	@Column(name = "expected_delivery")
	private int expectedDelivery;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public int getExpectedDelivery() {
		return expectedDelivery;
	}

	public void setExpectedDelivery(int expectedDelivery) {
		this.expectedDelivery = expectedDelivery;
	}
}
