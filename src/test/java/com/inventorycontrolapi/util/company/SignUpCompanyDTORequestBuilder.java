package com.inventorycontrolapi.util.company;

import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;

public abstract class SignUpCompanyDTORequestBuilder {
	public static SignUpCompanyDTORequest createWithValidData() {
		return new SignUpCompanyDTORequest(
			"Company A",
			"companya@gmail.com",
			"123"
		);
	}
	
	public static SignUpCompanyDTORequest createWithEmptyName() {
		return new SignUpCompanyDTORequest(
			"",
			"companya@gmail.com",
			"123"
		);
	}
	
	public static SignUpCompanyDTORequest createWithEmptyEmail() {
		return new SignUpCompanyDTORequest(
			"Company A",
			"",
			"123"
		);
	}
	
	public static SignUpCompanyDTORequest createWithInvalidEmailFormat() {
		return new SignUpCompanyDTORequest(
			"Company A",
			"@gmail.com",
			"123"
		);
	}
	
	public static SignUpCompanyDTORequest createWithEmptyPassword() {
		return new SignUpCompanyDTORequest(
			"Company A",
			"companya@gmail.com",
			""
		);
	}
}
