package com.inventorycontrolapi.util.itemCategory;

import java.util.UUID;

import com.inventorycontrolapi.dtos.itemCategory.UpdateItemCategoryDTORequest;

public abstract class UpdateItemCategoryDTORequestBuilder {
	public static UpdateItemCategoryDTORequest createWithValidData() {
		return new UpdateItemCategoryDTORequest("0", UUID.randomUUID().toString(), "Item Category B");
	}

	public static UpdateItemCategoryDTORequest createWithValidDataAndInvalidCompanyId() {
		return new UpdateItemCategoryDTORequest("0", UUID.randomUUID().toString(), "Item Category B");
	}
	
	public static UpdateItemCategoryDTORequest createWithEmptyName() {
		return new UpdateItemCategoryDTORequest("0", UUID.randomUUID().toString(), "");
	}
}
