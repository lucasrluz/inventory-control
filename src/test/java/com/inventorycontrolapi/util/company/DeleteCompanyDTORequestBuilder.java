package com.inventorycontrolapi.util.company;

import com.inventorycontrolapi.dtos.company.DeleteCompanyDTORequest;

public abstract class DeleteCompanyDTORequestBuilder {
	public static DeleteCompanyDTORequest createWithValidData() {
		return new DeleteCompanyDTORequest("0");
	}
}
