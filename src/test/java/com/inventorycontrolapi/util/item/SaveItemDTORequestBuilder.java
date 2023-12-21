package com.inventorycontrolapi.util.item;

import java.util.UUID;

import com.inventorycontrolapi.dtos.item.SaveItemDTORequest;

public abstract class SaveItemDTORequestBuilder {
	public static SaveItemDTORequest createWithValidData() {
		return new SaveItemDTORequest(UUID.randomUUID().toString(), "0", "Item A", "1.25", "1");
	}
	
	public static SaveItemDTORequest createWithEmptyName() {
		return new SaveItemDTORequest(UUID.randomUUID().toString(), "0", "", "1.25", "1");
	}
}
