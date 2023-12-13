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
@Table(name = "_item")
public class ItemModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long itemId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, name = "unit_price")
	private double unitPrice;

	@Column(nullable = false, name = "quantity_in_stock")
	private int quantityInStock;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private CompanyModel companyModel;

	@ManyToOne
	@JoinColumn(name = "item_category_id", nullable = false)
	private ItemCategoryModel itemCategoryModel;

	public ItemModel() {}

	public ItemModel(String name, double unitPrice, int quantityInStock, CompanyModel companyModel, ItemCategoryModel itemCategoryModel) {
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantityInStock = quantityInStock;
		this.companyModel = companyModel;
		this.itemCategoryModel = itemCategoryModel;
	}

	public ItemModel(Long itemId, String name, double unitPrice, int quantityInStock, CompanyModel companyModel, ItemCategoryModel itemCategoryModel) {
		this.itemId = itemId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantityInStock = quantityInStock;
		this.companyModel = companyModel;
		this.itemCategoryModel = itemCategoryModel;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantityInStock() {
		return quantityInStock;
	}

	public ItemCategoryModel getItemCategoryModel() {
		return itemCategoryModel;
	}

	public void setItemCategoryModel(ItemCategoryModel itemCategoryModel) {
		this.itemCategoryModel = itemCategoryModel;
	}

	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public CompanyModel getCompanyModel() {
		return companyModel;
	}

	public void setCompanyModel(CompanyModel companyModel) {
		this.companyModel = companyModel;
	}
}
