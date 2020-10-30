package com.example.springboot.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.springboot.demo.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("api/")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	/*
	 * @GetMapping("test") public String test() { return "users"; }
	 */
	
	//get users
	@GetMapping("companies/{companyId}/users")
	public Page<User> getAllUserByCompanyId(@PathVariable (value = "companyId") Long companyId,
											Pageable pageable) {
		return this.userRepository.findByCompanyId(companyId, pageable);
	}
	/*
	 * //get user by id
	 * 
	 * @GetMapping("users/{id}") public ResponseEntity<User>
	 * getUserById(@PathVariable(value = "id") Long userId) throws
	 * ResourceNotFoundException { User user = userRepository.findById(userId)
	 * .orElseThrow(() -> new
	 * ResourceNotFoundException("Ushbu Id li user topilmadi :: " + userId)); return
	 * ResponseEntity.ok().body(user); }
	 */
	
	//save user
	
	@PostMapping("companies/{companyId}/users")
	public User createUser(@PathVariable(value = "companyId") Long companyId,
						   @Validated @RequestBody User user) throws ResourceNotFoundException {
		return companyRepository.findById(companyId).map(company -> {
			user.setCompany(company);
			return this.userRepository.save(user);

		}).orElseThrow(() -> new ResourceNotFoundException("Ushbu id li company mavjud emas :: " + companyId));
	}
	
	//update user
	
	@PutMapping("companies/{companyId}/users/{userId}")
	public User updateUser(@PathVariable(value = "CompanyId") Long companyId,
										   @PathVariable(value = "userId") Long userId,
										   @Validated @RequestBody User userDetails) throws ResourceNotFoundException {
		if (!companyRepository.existsById(companyId)) {
			throw new ResourceNotFoundException("Ushbu id li company topilmadi :: " + companyId);
		}

		return userRepository.findById(userId).map(user -> {
		user.setEmail(userDetails.getEmail());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		return this.userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("Ushbu id li user topilmadi :: " + userId));




	}
	
	//delete user
	
	@DeleteMapping("companies/{companyId}/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "companyId") Long companyId,
										@PathVariable(value = "userId") Long userId) throws ResourceNotFoundException {
		
		return userRepository.findByIdAndCompanyId(userId, companyId).map(user -> {
			this.userRepository.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Ushbu Id li user topilmadi :: " + userId));
	}
}
