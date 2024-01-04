package com.inventorycontrolapi.util.company;

import com.inventorycontrolapi.dtos.company.UpdateCompanyDTORequest;

public abstract class UpdateCompanyDTORequestBuilder {
	public static UpdateCompanyDTORequest createWithValidData() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company B",
			"companyb@gmail.com",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyName() {
		return new UpdateCompanyDTORequest(
			"0",
			"",
			"companyb@gmail.com",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyEmail() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company B",
			"",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithInvalidEmailFormat() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company B",
			"@gmail.com",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyPassword() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company B",
			"companyb@gmail.com",
			""
		);
	}
}
