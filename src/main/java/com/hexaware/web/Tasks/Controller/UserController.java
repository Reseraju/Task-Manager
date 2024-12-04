package com.hexaware.web.Tasks.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.hexaware.web.Tasks.DTO.AuthenticationRequest;
import com.hexaware.web.Tasks.DTO.AuthenticationResponse;
import com.hexaware.web.Tasks.DTO.SignupRequest;
import com.hexaware.web.Tasks.DTO.UserDTO;
import com.hexaware.web.Tasks.Entity.Users;
import com.hexaware.web.Tasks.Repository.UserRepo;
import com.hexaware.web.Tasks.Service.AuthService;
import com.hexaware.web.Tasks.Service.JWTService;
import com.hexaware.web.Tasks.Service.UserPrincipalService;
import com.hexaware.web.Tasks.Service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private  AuthService authService;
    
    @Autowired
    private  AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserPrincipalService userPrincipalService;
	
	@Autowired
    private  UserRepo userRepository;
	
	@Autowired
    private  JWTService jwtService;

    // Add a new user
//    @PostMapping("/add")
//    public ResponseEntity<Users> addUser(@RequestBody Users user) {
//        Users savedUser = userService.addUser(user);
//        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
//    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Customer already exists", HttpStatus.NOT_ACCEPTABLE);

        UserDTO createdCustomerDto = authService.createUser(signupRequest);

        if (createdCustomerDto == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }
    
//    @PostMapping("/login")
//    public String login(@RequestBody Users user) {
//    	return userService.verify(user);
//    }

    // Get all users
//    @GetMapping("/all")
//    public ResponseEntity<List<Users>> getAllUsers() {
//        List<Users> users = userService.getAllUsers();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("Incorrect Email Or Password.");
        }
        
        final UserDetails userDetails = userPrincipalService.loadUserByUsername(authenticationRequest.getEmail());
        Optional<Users> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtService.generateToken(userDetails.getUsername());
        
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getUserId());
        }
        return ResponseEntity.ok(authenticationResponse);
    }
    
}
