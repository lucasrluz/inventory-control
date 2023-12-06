package com.inventorycontrolapi.util.itemCategory;

import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;

public abstract class SaveItemCategoryDTORequestBuilder {
	public static SaveItemCategoryDTORequest createWithValidData() {
		return new SaveItemCategoryDTORequest("Item Category A", "0");
	}
}
