package com.inventorycontrolapi.util.item;

import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;

public abstract class ItemModelBuilder{
	public static ItemModel createWithItemId(CompanyModel companyModel, ItemCategoryModel itemCategoryModel) {
		return new ItemModel(0L, "Item A", 1.25, 1, companyModel, itemCategoryModel);
	}
}
