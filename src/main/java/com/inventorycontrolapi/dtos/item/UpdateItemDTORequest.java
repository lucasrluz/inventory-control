package com.inventorycontrolapi.dtos.item;

public class UpdateItemDTORequest {
	private String itemId;
	private String companyId;
	private String name;
	private String unitPrice;
	private String quantityInStock;
	private String itemCategoryId;

	public UpdateItemDTORequest() {}

	public UpdateItemDTORequest(String itemId, String companyId, String name, String unitPrice, String quantityInStock, String itemCategoryId) {
		this.itemId = itemId;
		this.companyId = companyId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantityInStock = quantityInStock;
		this.itemCategoryId = itemCategoryId;
	}

	public String getItemId() {
		return itemId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(String quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
