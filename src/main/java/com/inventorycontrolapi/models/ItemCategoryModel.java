package com.inventorycontrolapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "_item_category")
public class ItemCategoryModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemCategoryId;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyModel companyModel;

	public ItemCategoryModel() {}

	public ItemCategoryModel(String name, CompanyModel companyModel) {
		this.name = name;
		this.companyModel = companyModel;
	}

	public ItemCategoryModel(Long itemCategoryId, String name, CompanyModel companyModel) {
		this.itemCategoryId = itemCategoryId;
		this.name = name;
		this.companyModel = companyModel;
	}

	public Long getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(Long itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}
}
