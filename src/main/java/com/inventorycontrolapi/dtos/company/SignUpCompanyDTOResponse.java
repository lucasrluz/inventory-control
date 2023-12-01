package com.inventorycontrolapi.dtos.company;

public class SignUpCompanyDTOResponse {
	private String companyId;

	public SignUpCompanyDTOResponse() {}

	public SignUpCompanyDTOResponse(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
