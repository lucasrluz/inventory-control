package com.inventorycontrolapi.dtos.company;

public class UpdateCompanyDTORequest {
	private String companyId;
	private String name;
	private String email;
	private String password;

	public UpdateCompanyDTORequest() {}

	public UpdateCompanyDTORequest(String companyId, String name, String email, String password) {
		this.companyId = companyId;
		this.name = name;
		this.email = email;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
