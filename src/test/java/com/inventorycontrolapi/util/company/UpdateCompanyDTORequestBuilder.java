package com.inventorycontrolapi.util.company;

import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.UpdateCompanyDTORequest;

public abstract class UpdateCompanyDTORequestBuilder {
	public static UpdateCompanyDTORequest createWithValidData() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company A",
			"companya@gmail.com",
			"123"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyName() {
		return new UpdateCompanyDTORequest(
			"0",
			"",
			"companya@gmail.com",
			"123"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyEmail() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company A",
			"",
			"123"
		);
	}
	
	public static UpdateCompanyDTORequest createWithInvalidEmailFormat() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company A",
			"@gmail.com",
			"123"
		);
	}
	
	public static UpdateCompanyDTORequest createWithEmptyPassword() {
		return new UpdateCompanyDTORequest(
			"0",
			"Company A",
			"companya@gmail.com",
			""
		);
	}
}
