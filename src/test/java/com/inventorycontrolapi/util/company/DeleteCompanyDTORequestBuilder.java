package com.inventorycontrolapi.util.company;

import java.util.UUID;

import com.inventorycontrolapi.dtos.company.DeleteCompanyDTORequest;

public abstract class DeleteCompanyDTORequestBuilder {
	public static DeleteCompanyDTORequest createWithValidData() {
		return new DeleteCompanyDTORequest(UUID.randomUUID().toString());
	}
}
