package com.inventorycontrolapi.dtos.itemCategory;

public class DeleteItemCategoryDTOResponse {
	public String itemCategoryId;

	public DeleteItemCategoryDTOResponse() {}

	public DeleteItemCategoryDTOResponse(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
}
