package com.codingdojo.Cashx.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.Cashx.models.Product;
import com.codingdojo.Cashx.repositories.ProductRepository;







@Service
public class ProductService {

	@Autowired
	private ProductRepository productServ;

	public List<Product> allProduct() {
		return productServ.findAll();
	}
	

	public Product createProduct(Product product) {

        Product existingProduct = productServ.findByName(product.getName());

        if (existingProduct != null) {
            int existingQuantity = existingProduct.getQuantity();
            // Si le produit existe déjà, mettez à jour ses informations
            existingProduct.setQuantity(existingQuantity+product.getQuantity());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());

            return productServ.save(existingProduct);
        } else {
            return productServ.save(product);
        }
    }
	public Product updateproduct(Product product) {
			return productServ.save(product);
	}
	public void deleteProduct(Long id) {
		productServ.deleteById(id);
	}
	public Product findProduct(Long id) {
        Optional<Product> optionalProduct = productServ.findById(id);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            return null;
        }
    }

}
