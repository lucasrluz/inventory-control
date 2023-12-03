package com.inventorycontrolapi.dtos.company;

public class SignInCompanyDTOResponse {
	private String jwt;

	public SignInCompanyDTOResponse() {}

	public SignInCompanyDTOResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
