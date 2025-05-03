package com.codingdojo.Cashx.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.codingdojo.Cashx.models.Invoice;
import com.codingdojo.Cashx.models.InvoicesProduct;



public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
	
	List<Invoice> findAll();
	
	@Query(value = "SELECT * FROM invoices ORDER BY created_at DESC LIMIT 3", nativeQuery = true)
	List<Invoice> findLast();
	@Query(value = "SELECT clients.business_name, COUNT(invoices.id) AS num_invoices\r\n"
			+ "FROM clients\r\n"
			+ "JOIN invoices ON clients.id = invoices.client_id\r\n"
			+ "GROUP BY clients.id, clients.business_name\r\n"
			+ "LIMIT 3;", nativeQuery = true)
	List<Object[]> TopClient();
}
