package com.inventorycontrolapi.util.itemCategory;

import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTORequest;

public abstract class GetAllItemCategoryDTORequestBuilder {
	public static GetAllItemCategoryDTORequest createWithValidData() {
		return new GetAllItemCategoryDTORequest("0");
	}
}
