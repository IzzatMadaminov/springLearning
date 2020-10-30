package com.example.springboot.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
import com.example.springboot.demo.model.Company;
import com.example.springboot.demo.repository.CompanyRepository;

@RestController
@RequestMapping("api/")
public class CompanyController {
	
	@Autowired 
	private CompanyRepository companyRepository;

	@GetMapping("companies")
	public Page<Company> getAllCompanies (Pageable pageable) {
		return this.companyRepository.findAll(pageable);
	}
	
	@GetMapping("companies/{id}")
	public ResponseEntity<Company> getCompanyById (@PathVariable(value = "id") Long companyId) 
			throws ResourceNotFoundException {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Ushbu id li company topilmadi :: " + companyId));
		return ResponseEntity.ok().body(company);
	}
	
	@PostMapping("companies")
	public Company createCompany (@Validated @RequestBody Company company) {
		return this.companyRepository.save(company);
	}
	
	@PutMapping("companies/{id}")
	public ResponseEntity<Company> updateCompany (@PathVariable(value = "id") Long companyId,
			@Validated @RequestBody Company companyDetails) throws ResourceNotFoundException {
		Company company = this.companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Ushbu id li company topilmaid :: " + companyId));
		company.setAddress(companyDetails.getAddress());
		company.setName(companyDetails.getName());
		company.setPhone(companyDetails.getPhone());
		
		return ResponseEntity.ok(this.companyRepository.save(company));
	} 
	
	@DeleteMapping("companies/{id}")
	public Map<String, Boolean> deleteCompany (@PathVariable(value = "id") Long companyId) throws ResourceNotFoundException {
		Company company = this.companyRepository.findById(companyId)
				.orElseThrow(() -> new ResourceNotFoundException("Ushbu id li company topilmaid :: " + companyId));
		this.companyRepository.delete(company);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
	}
}
