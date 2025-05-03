package com.codingdojo.Cashx.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "invoices")
public class Invoice {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
	    private User creator;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "client_id")
	    private Client client;

	    
	    
	    @OneToMany(mappedBy="invoice", fetch=FetchType.LAZY)
	    private List<InvoicesProduct> productsbyinvoice;

	    
	    
	    
	    @Column(updatable = false)
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date createdAt;

	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date updatedAt;
	    @Column(unique = true)
	    private String numeroFacture;

	    public Invoice() {}

	    @PrePersist
	    protected void onCreate() {
	        this.createdAt = new Date();
	        genererNumeroFacture();
	    }

	    @PreUpdate
	    protected void onUpdate() {
	        this.updatedAt = new Date();
	    }
	    private void genererNumeroFacture() {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	        String datePart = sdf.format(new Date());

	       
	        Random rand = new Random();
	        int randomInt = rand.nextInt(10000); 

	        this.numeroFacture = datePart + "-" + randomInt;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public User getCreator() {
			return creator;
		}

		public void setCreator(User creator) {
			this.creator = creator;
		}

		public Client getClient() {
			return client;
		}

		public void setClient(Client client) {
			this.client = client;
		}

	

		public Date getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}

		public Date getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}

		public String getNumeroFacture() {
			return numeroFacture;
		}

		public void setNumeroFacture(String numeroFacture) {
			this.numeroFacture = numeroFacture;
		}

		public List<InvoicesProduct> getProductsbyinvoice() {
			return productsbyinvoice;
		}

		public void setProductsbyinvoice(List<InvoicesProduct> productsbyinvoice) {
			this.productsbyinvoice = productsbyinvoice;
		}

		

		
	    
}
