package com.codingdojo.Cashx.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.Cashx.models.LoginUser;
import com.codingdojo.Cashx.models.User;
import com.codingdojo.Cashx.repositories.UserRepository;






@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;

	// TO-DO: Write register and login methods!
	public User register(User newUser, BindingResult result) {
		// TO-DO: Additional validations!
		Optional<User> potentialUser = userRepo.findByEmail(newUser.getEmail());
		if (potentialUser.isPresent()) {
			result.rejectValue("email", "registerError", "Email is Taken");
		}
		if (!newUser.getPassword().equals(newUser.getConfirm())) {
			result.rejectValue("password", "registerError", "Password odes not match");

		}
		if (result.hasErrors()) {
			return null;
		} else {
			String hashedPw = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
			newUser.setPassword(hashedPw);
			return userRepo.save(newUser);
		}
	}

	public User login(LoginUser newLoginObject, BindingResult result) {
		// TO-DO: Additional validations!
		Optional<User> potentialUser = userRepo.findByEmail(newLoginObject.getEmail());
		if (!potentialUser.isPresent()) {
			result.rejectValue("email", "loginError", "Email is not found");
		} else {
			User user = potentialUser.get();
			if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
				result.rejectValue("password", "loginError", "Invalid Password!");
			}
			if (result.hasErrors()) {
				return null;
			} else {
				return user;
			}
		}
		return null;
	}

	 public User findUserById(Long id) {
	    	Optional<User> maybeUser = userRepo.findById(id);
	    	if(maybeUser.isPresent()) {
	    		return maybeUser.get();
	    	}else {
	    		return null;
	    	}
	    }
}
