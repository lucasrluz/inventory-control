package com.inventorycontrolapi.dtos.item;

public class GetAllItemDTORequest {
	private String companyId;

	public GetAllItemDTORequest(){}

	public GetAllItemDTORequest(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
