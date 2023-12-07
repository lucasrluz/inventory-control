package com.inventorycontrolapi.dtos.itemCategory;

public class UpdateItemCategoryDTORequest {
	private String itemCategoryId;
	private String companyId;
	private String name;

	public UpdateItemCategoryDTORequest() {}

	public UpdateItemCategoryDTORequest(String itemCategoryId, String companyId, String name) {
		this.itemCategoryId = itemCategoryId;
		this.companyId = companyId;
		this.name = name;
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
}
