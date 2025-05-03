package com.codingdojo.Cashx.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codingdojo.Cashx.models.InvoicesProduct;
import com.codingdojo.Cashx.models.Product;





public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findAll();

	Product findByName(String name);
	
	
}
