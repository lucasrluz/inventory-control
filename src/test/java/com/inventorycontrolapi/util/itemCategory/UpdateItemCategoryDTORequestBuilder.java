package com.inventorycontrolapi.util.itemCategory;

import com.inventorycontrolapi.dtos.itemCategory.UpdateItemCategoryDTORequest;

public abstract class UpdateItemCategoryDTORequestBuilder {
	public static UpdateItemCategoryDTORequest createWithValidData() {
		return new UpdateItemCategoryDTORequest("0", "0", "Item Category B");
	}

	public static UpdateItemCategoryDTORequest createWithValidDataAndInvalidCompanyId() {
		return new UpdateItemCategoryDTORequest("0", "1", "Item Category B");
	}
	
	public static UpdateItemCategoryDTORequest createWithEmptyName() {
		return new UpdateItemCategoryDTORequest("0", "0", "");
	}
}
