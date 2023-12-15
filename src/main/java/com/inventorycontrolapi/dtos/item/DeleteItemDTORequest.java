package com.inventorycontrolapi.dtos.item;

public class DeleteItemDTORequest {
	private String itemId;
	private String companyId;

	public DeleteItemDTORequest() {}

	public DeleteItemDTORequest(String itemId, String companyId) {
		this.itemId = itemId;
		this.companyId = companyId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
