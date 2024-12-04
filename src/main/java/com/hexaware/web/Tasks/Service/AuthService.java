package com.hexaware.web.Tasks.Service;

import com.hexaware.web.Tasks.DTO.SignupRequest;
import com.hexaware.web.Tasks.DTO.UserDTO;

public interface AuthService {
	UserDTO createUser(SignupRequest signupRequest);
	
	boolean hasCustomerWithEmail(String email);
}
