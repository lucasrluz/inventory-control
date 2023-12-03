package com.inventorycontrolapi.dtos.company;

public class SignInCompanyDTORequest {
	private String email;
	private String password;

	public SignInCompanyDTORequest() {}

	public SignInCompanyDTORequest(String email, String password) {
		this.email = email;
		this.password = password;
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
