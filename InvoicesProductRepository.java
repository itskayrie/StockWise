package com.codingdojo.Cashx.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codingdojo.Cashx.models.InvoicesProduct;





public interface InvoicesProductRepository extends CrudRepository<InvoicesProduct, Long> {

	List<InvoicesProduct> findAll();
	
	@Query(value="SELECT * FROM invoices_products where invoice_id= ?1 ", nativeQuery=true)
	List<InvoicesProduct>findAllbyInvoice(Long id);
	
//	 @Query(value = "SELECT * FROM invoices_products ORDER BY product_quantity DESC LIMIT 5", nativeQuery = true)
//	    List<InvoicesProduct> getTop();
	 
	 @Query(value = "SELECT * FROM invoices_products ORDER BY product_quantity DESC LIMIT 5", nativeQuery = true)
	 List<InvoicesProduct> getTop();
	 
	 
	 @Query(value = "SELECT SUM(sub.total_quantity * p.price) AS total_value\r\n"
	 		+ "FROM product p\r\n"
	 		+ "JOIN (\r\n"
	 		+ "    SELECT product_id, SUM(product_quantity) as total_quantity\r\n"
	 		+ "    FROM invoices_products\r\n"
	 		+ "    GROUP BY product_id\r\n"
	 		+ ") sub\r\n"
	 		+ "ON p.id = sub.product_id\r\n"
	 		+ "WHERE p.created_at >= DATE_SUB(NOW(), INTERVAL 1 MONTH);\r\n"
	 		+ "", nativeQuery = true)
	 Double LastMonthTurnover();

}
