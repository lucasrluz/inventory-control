package com.inventorycontrolapi.dtos.itemCategory;

public class SaveItemCategoryDTORequest{
	private String name;
	private String companyId;

	public SaveItemCategoryDTORequest() {}

	public SaveItemCategoryDTORequest(String name, String companyId) {
		this.name = name;
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
