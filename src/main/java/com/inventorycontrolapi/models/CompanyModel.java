package com.inventorycontrolapi.models;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "_company")
public class CompanyModel {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID companyId;	

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@OneToMany(mappedBy = "companyModel")
	private Set<ItemCategoryModel> itemCategoryModels;

	@OneToMany(mappedBy = "companyModel")
	private Set<ItemModel> itemModels;

	public CompanyModel() {}

	public CompanyModel(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public CompanyModel(UUID companyId, String name, String email, String password) {
		this.companyId = companyId;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public UUID getCompanyId() {
		return companyId;
	}

	public void setCompanyId(UUID companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ItemCategoryModel> getItemCategoryModels() {
		return itemCategoryModels;
	}

	public Set<ItemModel> getItemModels() {
		return itemModels;
	}

	public void setItemModels(Set<ItemModel> itemModels) {
		this.itemModels = itemModels;
	}

	public void setItemCategoryModels(Set<ItemCategoryModel> itemCategoryModels) {
		this.itemCategoryModels = itemCategoryModels;
	}
}
