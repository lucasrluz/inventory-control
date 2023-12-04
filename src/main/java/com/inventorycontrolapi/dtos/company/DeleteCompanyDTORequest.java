package com.inventorycontrolapi.dtos.company;

public class DeleteCompanyDTORequest {
	private String companyId;

	public DeleteCompanyDTORequest() {}

	public DeleteCompanyDTORequest(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
