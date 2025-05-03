package com.codingdojo.Cashx.models;

import java.util.Date;
import java.util.List;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="clients")
public class Client {
	// MEMBER VARIABLES
			@Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
			private Long id;
			
			@NotEmpty
			@Size(min = 3, max = 200, message ="entrer un nom !")
			private String Name;
			
			@NotEmpty
			@Size(min = 3, max = 100, message ="nom de l'entreprise svp")
			private String businessName;
			
			@NotEmpty
			@Size(min = 4, max = 100, message ="adresse manquante")
			private String Adresse;
			
			@NotNull
		    @Min(10000000)
		    private Integer PhoneNumber; 
			
			

			// This will not allow the createdAt column to be updated after creation
			 @Column(updatable=false)
			 @DateTimeFormat(pattern="yyyy-MM-dd")
			 private Date createdAt;
			 @DateTimeFormat(pattern="yyyy-MM-dd")
			 private Date updatedAt;

			 @ManyToOne(fetch = FetchType.LAZY)
			 @JoinColumn(name="user_id")
			 private User user;
			 
			 @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
			   	private List<Invoice> invoices;
			 
		 // zero args construtor 
			public Client() {
			}
			
			@PrePersist
			protected void onCreate() {
				this.createdAt = new Date();
			}

			@PreUpdate
			protected void onUpdate() {
				this.updatedAt = new Date();
			}

			public Long getId() {
				return id;
			}
			public String getName() {
				return Name;
			}
			public void setName(String name) {
				Name = name;
			}
			public String getBusinessName() {
				return businessName;
			}
			public void setBusinessName(String businessName) {
				this.businessName = businessName;
			}
			public String getAdresse() {
				return Adresse;
			}
			public void setAdresse(String adresse) {
				Adresse = adresse;
			}
			public Integer getPhoneNumber() {
				return PhoneNumber;
			}
			public void setPhoneNumber(Integer phoneNumber) {
				PhoneNumber = phoneNumber;
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
			public User getUser() {
				return user;
			}
			public void setUser(User user) {
				this.user = user;
			}
			public void setId(Long id) {
				this.id = id;
			}
			
			
		    
}
