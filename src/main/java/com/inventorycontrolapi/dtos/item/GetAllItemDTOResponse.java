package com.inventorycontrolapi.dtos.item;

public class GetAllItemDTOResponse {
	private String itemId;
	private String name;
	private String unitPrice;
	private String quantityInStock;
	private String itemCategory;

	public GetAllItemDTOResponse() {}

	public GetAllItemDTOResponse(String itemId, String name, String unitPrice, String quantityInStock, String itemCategory) {
		this.itemId = itemId;
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantityInStock = quantityInStock;
		this.itemCategory = itemCategory;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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
