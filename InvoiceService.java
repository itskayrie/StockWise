package com.codingdojo.Cashx.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.Cashx.models.Invoice;
import com.codingdojo.Cashx.models.InvoicesProduct;
import com.codingdojo.Cashx.repositories.InvoiceRepository;
import com.codingdojo.Cashx.repositories.InvoicesProductRepository;










@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository invoiceServ;
	@Autowired
    private InvoicesProductRepository invoiceProductRepository;

	public List<Invoice> allInvoice() {
		return invoiceServ.findAll();
	}
	public List<Invoice> lastInvoice() {
		return invoiceServ.findLast();
	}
	public List<Object[]> topclient() {
		return invoiceServ.TopClient();
	}

	public Invoice createInvoice(Invoice p) {
		return invoiceServ.save(p);
	}
	public void deleteInvoice(Long id) {
		  Optional<Invoice> optionalInvoice = invoiceServ.findById(id);
	        if (optionalInvoice.isPresent()) {
	            Invoice invoice = optionalInvoice.get();

	            // Find associated records in invoices_products table
	            List<InvoicesProduct> associatedProducts = invoiceProductRepository.findAllbyInvoice(id);

	            // Delete associated records
	            for (InvoicesProduct product : associatedProducts) {
	                invoiceProductRepository.delete(product);
	            }
	            invoiceServ.deleteById(id);}
	}
	    
	public Invoice findInvoice(Long id) {
        Optional<Invoice> optionalInvoice = invoiceServ.findById(id);
        if(optionalInvoice.isPresent()) {
            return optionalInvoice.get();
        } else {
            return null;
        }
    }

}
