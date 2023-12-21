package com.inventorycontrolapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventorycontrolapi.models.CompanyModel;

public interface CompanyRepository extends JpaRepository<CompanyModel, UUID> {
	public Optional<CompanyModel> findByEmail(String email);
}
