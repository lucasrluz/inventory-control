package com.inventorycontrolapi.dtos.company;

public class UpdateCompanyDTOResponse {
	private String companyId;
	private String name;
	private String email;

	public UpdateCompanyDTOResponse() {}

	public UpdateCompanyDTOResponse(String companyId, String name, String email) {
		this.companyId = companyId;
		this.name = name;
		this.email = email;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
