package com.nagarro.training.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.training.models.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
	// Find By Product Name
	Product findByPname(String pname);

	// Find By Product Brand
	List<Product> findByBrand(String brand);

	// Find By Product id & Name
	List<Product> findByPidOrPname(int pid, String pname);

	// Find By Product id & brand
	List<Product> findByPidOrBrand(int pid, String brand);

	// Find By Product Name & Brand
	List<Product> findByPnameOrBrand(String pname, String brand);

	// Find By Product id, Name & Brand
	List<Product> findByPidOrPnameOrBrand(int pid, String pname, String brand);
}
