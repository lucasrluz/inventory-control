package com.inventorycontrolapi.dtos.company;

public class DeleteCompanyDTOResponse {
	private String companyId;

	public DeleteCompanyDTOResponse() {}

	public DeleteCompanyDTOResponse(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
