package com.inventorycontrolapi.util.company;

import java.util.UUID;

import com.inventorycontrolapi.dtos.company.GetCompanyDTORequest;

public abstract class GetCompanyDTORequestBuilder {
	public static GetCompanyDTORequest createWithValidData() {
		return new GetCompanyDTORequest(
			UUID.randomUUID().toString()	
		);
	}
}
