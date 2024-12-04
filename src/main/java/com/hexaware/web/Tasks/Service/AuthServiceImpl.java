package com.hexaware.web.Tasks.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.hexaware.web.Tasks.DTO.SignupRequest;
import com.hexaware.web.Tasks.DTO.UserDTO;
import com.hexaware.web.Tasks.Entity.Users;
import com.hexaware.web.Tasks.Repository.UserRepo;

@Component
public class AuthServiceImpl implements AuthService {
	
	@Autowired
    private  UserRepo userRepository;

	@Override
	public UserDTO createUser(SignupRequest signupRequest) {
	    Users user = new Users();
	    user.setUsername(signupRequest.getUsername());
	    user.setEmail(signupRequest.getEmail());
	    user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));

	    Users createdUser = userRepository.save(user);

	    // Convert User to UserDTO to hide unnecessary fields
	    UserDTO userDTO = new UserDTO();
	    userDTO.setId(createdUser.getUserId());
	    userDTO.setUsername(createdUser.getUsername());
	    userDTO.setEmail(createdUser.getEmail());
	    return userDTO;  // Rewritten line without hidden characters
	}
	
	

	@Override
	public boolean hasCustomerWithEmail(String email) {
		return false;
	}

}
