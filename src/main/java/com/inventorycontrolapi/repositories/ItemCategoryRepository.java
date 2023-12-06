package com.inventorycontrolapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;

@Repository
public interface ItemCategoryRepository extends JpaRepository<ItemCategoryModel, Long> {
	List<ItemCategoryModel> findByCompanyModel(CompanyModel companyModel);
}
