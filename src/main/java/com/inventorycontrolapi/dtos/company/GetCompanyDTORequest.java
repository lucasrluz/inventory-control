package com.inventorycontrolapi.dtos.company;

public class GetCompanyDTORequest {
	private String companyId;

	public GetCompanyDTORequest() {}

	public GetCompanyDTORequest(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
