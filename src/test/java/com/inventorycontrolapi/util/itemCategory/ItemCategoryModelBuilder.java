package com.inventorycontrolapi.util.itemCategory;

import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;

public abstract class ItemCategoryModelBuilder {
	public static ItemCategoryModel createWithItemCategoryId(CompanyModel companyModel) {
		return new ItemCategoryModel(0L, "Item Category A", companyModel);
	}
}
