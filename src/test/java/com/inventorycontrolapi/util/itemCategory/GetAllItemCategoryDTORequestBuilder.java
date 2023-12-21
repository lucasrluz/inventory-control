package com.inventorycontrolapi.util.itemCategory;

import java.util.UUID;

import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTORequest;

public abstract class GetAllItemCategoryDTORequestBuilder {
	public static GetAllItemCategoryDTORequest createWithValidData() {
		return new GetAllItemCategoryDTORequest(UUID.randomUUID().toString());
	}
}
