package com.hexaware.web.Tasks.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.web.Tasks.Entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
	
	Users findByEmail(String email);
	
	Optional<Users> findFirstByEmail(String email);
}
