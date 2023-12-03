package com.inventorycontrolapi.util.company;

import com.inventorycontrolapi.dtos.company.SignInCompanyDTORequest;

public abstract class SignInCompanyDTORequestBuilder {
	public static SignInCompanyDTORequest createWithValidData() {
		return new SignInCompanyDTORequest("companya@gmail.com", "123");
	}
}
