package com.codingdojo.Cashx.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.Cashx.models.Client;


@Repository
public interface ClientsRepository extends CrudRepository<Client, Long> {
	List<Client>findAll();
}
