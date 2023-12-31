package com.inventorycontrolapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemModel;

@Repository
public interface ItemRepository extends JpaRepository<ItemModel, Long> {
	Optional<ItemModel> findByName(String name);
	List<ItemModel> findByCompanyModel(CompanyModel companyModel);
}
