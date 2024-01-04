package com.inventorycontrolapi.util.itemCategory;

import com.inventorycontrolapi.dtos.itemCategory.DeleteItemCategoryDTORequest;

public abstract class DeleteItemCategoryDTORequestBuilder {
	public static DeleteItemCategoryDTORequest createWithValidData() {
		return new DeleteItemCategoryDTORequest("0", "0");
	}
}
