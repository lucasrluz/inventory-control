package com.inventorycontrolapi.dtos.item;

public class DeleteItemDTOResponse {
	private String itemId;

	public DeleteItemDTOResponse() {}

	public DeleteItemDTOResponse(String itemId) {
		this.itemId = itemId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
