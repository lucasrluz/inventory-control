package com.inventorycontrolapi.dtos.item;

public class SaveItemDTOResponse {
	private String itemId;
	
	public SaveItemDTOResponse() {}

	public SaveItemDTOResponse(String itemId) {
		this.itemId = itemId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
