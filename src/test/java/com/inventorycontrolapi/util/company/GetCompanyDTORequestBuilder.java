package com.inventorycontrolapi.util.company;

import com.inventorycontrolapi.dtos.company.GetCompanyDTORequest;

public abstract class GetCompanyDTORequestBuilder {
	public static GetCompanyDTORequest createWithValidData() {
		return new GetCompanyDTORequest(
			"0"
		);
	}
}
