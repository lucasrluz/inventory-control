package com.inventorycontrolapi.util.company;

import java.util.UUID;

import com.inventorycontrolapi.dtos.company.UpdateCompanyDTORequest;

public abstract class UpdateCompanyDTORequestBuilder {
	public static UpdateCompanyDTORequest createWithValidData() {
		return new UpdateCompanyDTORequest(
			UUID.randomUUID().toString(),
			"Company B",
			"companyb@gmail.com",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyName() {
		return new UpdateCompanyDTORequest(
			UUID.randomUUID().toString(),
			"",
			"companyb@gmail.com",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyEmail() {
		return new UpdateCompanyDTORequest(
			UUID.randomUUID().toString(),
			"Company B",
			"",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithInvalidEmailFormat() {
		return new UpdateCompanyDTORequest(
			UUID.randomUUID().toString(),
			"Company B",
			"@gmail.com",
			"456"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyPassword() {
		return new UpdateCompanyDTORequest(
			UUID.randomUUID().toString(),
			"Company B",
			"companyb@gmail.com",
			""
		);
	}
}
