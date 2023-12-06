package com.inventorycontrolapi.dtos.itemCategory;

public class GetAllItemCategoryDTORequest {
	private String companyId;

	public GetAllItemCategoryDTORequest() {}

	public GetAllItemCategoryDTORequest(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
