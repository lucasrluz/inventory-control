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
}
