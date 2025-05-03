package com.codingdojo.Cashx.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.Cashx.models.User;





public interface UserRepository extends CrudRepository<User, Long> {

	  Optional<User> findByEmail(String email);
}
