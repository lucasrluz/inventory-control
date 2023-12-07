package com.inventorycontrolapi.dtos.itemCategory;

public class UpdateItemCategoryDTOResponse {
	private String itemCategoryId;
	private String name;

	public UpdateItemCategoryDTOResponse() {}

	public UpdateItemCategoryDTOResponse(String itemCategoryId, String name) {
		this.itemCategoryId = itemCategoryId;
		this.name = name;
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
}
