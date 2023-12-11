package com.inventorycontrolapi.dtos.itemCategory;

public class DeleteItemCategoryDTORequest {
	public String itemCategoryId;
	public String companyId;

	public DeleteItemCategoryDTORequest() {}

	public DeleteItemCategoryDTORequest(String itemCategoryId, String companyId) {
		this.itemCategoryId = itemCategoryId;
		this.companyId = companyId;
	}

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
