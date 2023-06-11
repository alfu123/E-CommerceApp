package com.nagarro.training.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "products")
public class Product {
	@Id
	@Column(name = "id")
	private int pid;

	@Column(name = "name")
	private String pname;

	@Column(name = "brand")
	private String brand;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private double price;

	@Column(name = "image_url")
	private String imageUrl;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private List<Serviceability> serviceability = new ArrayList<>();

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

	public List<Serviceability> getServiceability() {
		return serviceability;
	}

	public void setServiceability(List<Serviceability> serviceability) {
		this.serviceability = serviceability;
	}
}
