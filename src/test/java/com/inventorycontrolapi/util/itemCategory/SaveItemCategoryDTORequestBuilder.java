package com.inventorycontrolapi.util.itemCategory;

import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;

public abstract class SaveItemCategoryDTORequestBuilder {
	public static SaveItemCategoryDTORequest createWithValidData() {
		return new SaveItemCategoryDTORequest("Item Category A", "0");
	}

	public static SaveItemCategoryDTORequest createWithEmptyName() {
		return new SaveItemCategoryDTORequest("", "0");
	}
}
