package com.inventorycontrolapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventorycontrolapi.models.CompanyModel;

public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
	public Optional<CompanyModel> findByEmail(String email);
}
