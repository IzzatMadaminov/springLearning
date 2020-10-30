package com.example.springboot.demo.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Page<User> findByCompanyId(Long companyId, Pageable pageable);
	Optional<User> findByIdAndCompanyId(Long id, Long companyId);

}
