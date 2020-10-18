package com.example.springboot.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.demo.exception.ResourceNotFoundException;
import com.example.springboot.demo.model.User;
import com.example.springboot.demo.repository.UserRepository;

@RestController
@RequestMapping("api/v1/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("test")
	public String test() {
		return "users";
	}
	
	//get users
	@GetMapping("users")
	public List<User> getAllUser() {
		return this.userRepository.findAll();
	}
	
	//get user by id
	
	@GetMapping("users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Ushbu Id li user topilmadi ::" + userId));
		return ResponseEntity.ok().body(user);
	}
	
	//save user
	
	@PostMapping("users")
	public User createUser(@RequestBody User user) {
		return this.userRepository.save(user);
	}
	
	//update user
	
	@PutMapping("users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
			@Validated @RequestBody User userDetails) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Ushbu Id li user topilmadi ::" + userId));
		
		user.setEmail(userDetails.getEmail());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		
		return ResponseEntity.ok(this.userRepository.save(user));
		
	}
	
	//delete user
	
	@DeleteMapping("users/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
		
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Ushbu Id li user topilmadi ::" + userId));
		this.userRepository.delete(user);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}
}
