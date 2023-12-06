package com.inventorycontrolapi.dtos.itemCategory;

public class SaveItemCategoryDTOResponse {
	private String itemCategoryId;

	public SaveItemCategoryDTOResponse() {}

	public SaveItemCategoryDTOResponse(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
}
