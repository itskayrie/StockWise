package com.codingdojo.Cashx.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.Cashx.models.InvoicesProduct;
import com.codingdojo.Cashx.repositories.InvoicesProductRepository;


@Service
public class InvoicesProductService {

	@Autowired
	private InvoicesProductRepository invoiceProductRep;

	public List<InvoicesProduct> allInvoicesPro() {
		return invoiceProductRep.findAll();
	}
	
	public List<InvoicesProduct> TopProduct() {
		return invoiceProductRep.getTop();
	}

	public  Double LastMonth() {
		return invoiceProductRep.LastMonthTurnover();
	}
	
	public List<InvoicesProduct> allproductbyInvoice(Long invoice_id) {
		return invoiceProductRep.findAllbyInvoice(invoice_id);
	}

	public InvoicesProduct createInvoice(InvoicesProduct p) {
		return invoiceProductRep.save(p);
	}
	public Iterable<InvoicesProduct> createInvoicesProduct(Iterable<InvoicesProduct> invoiceProducts) {
        return invoiceProductRep.saveAll(invoiceProducts);
    }
	
	public void deleteInvoice (Long id) {
		invoiceProductRep.deleteById(id);
	}
	public InvoicesProduct findInvoice(Long id) {
        Optional<InvoicesProduct> optionalInvoicePro = invoiceProductRep.findById(id);
        if(optionalInvoicePro.isPresent()) {
            return optionalInvoicePro.get();
        } else {
            return null;
        }
    }

}
