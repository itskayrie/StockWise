package com.codingdojo.Cashx.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.Cashx.models.Client;
import com.codingdojo.Cashx.repositories.ClientsRepository;





@Service
public class ClientsService {
	

	// DI
	@Autowired
	private ClientsRepository ProRepo;
	// READ ALL
	public List<Client> allClients(){
		return ProRepo.findAll();
	}
	// CREATE
	public Client createClient(Client b) {
		return ProRepo.save(b);
	}
	
	
	// READ ONE
	public Client findClientById(Long id) {
		Optional<Client> maybeClient = ProRepo.findById(id);
		if(maybeClient.isPresent()) {
			return maybeClient.get();
		}else {
			return null;
		}
	}
	
	// UPDATE
	public Client updateClient(Client b) {
		return ProRepo.save(b);
	}
	
	// DELETE
	public void deleteClient(Long id) {
		ProRepo.deleteById(id);
	}
	
}
