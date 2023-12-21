package com.inventorycontrolapi.util.itemCategory;

import java.util.UUID;

import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;

public abstract class SaveItemCategoryDTORequestBuilder {
	public static SaveItemCategoryDTORequest createWithValidData() {
		return new SaveItemCategoryDTORequest("Item Category A", UUID.randomUUID().toString());
	}

	public static SaveItemCategoryDTORequest createWithEmptyName() {
		return new SaveItemCategoryDTORequest("", UUID.randomUUID().toString());
	}
}
