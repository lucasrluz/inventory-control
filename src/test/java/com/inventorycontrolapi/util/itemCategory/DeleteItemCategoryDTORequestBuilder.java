package com.inventorycontrolapi.util.itemCategory;

import java.util.UUID;

import com.inventorycontrolapi.dtos.itemCategory.DeleteItemCategoryDTORequest;

public abstract class DeleteItemCategoryDTORequestBuilder {
	public static DeleteItemCategoryDTORequest createWithValidData() {
		return new DeleteItemCategoryDTORequest("0", UUID.randomUUID().toString());
	}
}
