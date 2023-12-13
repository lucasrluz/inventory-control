package com.inventorycontrolapi.dtos.item;

public class SaveItemDTORequest {
	private String companyId;
	private String itemCategoryId;
	private String name;
	private String unitPrice;
	private String quantityInStock;

	public SaveItemDTORequest() {}

	public SaveItemDTORequest(String companyId, String itemCategoryId, String name, String unitPrice, String quantityInStock) {
		this.companyId = companyId;
		this.itemCategoryId = itemCategoryId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantityInStock = quantityInStock;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
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
}
