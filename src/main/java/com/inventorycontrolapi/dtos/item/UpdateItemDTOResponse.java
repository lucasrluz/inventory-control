package com.inventorycontrolapi.dtos.item;

public class UpdateItemDTOResponse {
	private String itemId;

	public UpdateItemDTOResponse() {}

	public UpdateItemDTOResponse(String itemId) {
		this.itemId = itemId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
