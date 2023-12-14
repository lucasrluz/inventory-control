package com.inventorycontrolapi.dtos.item;

public class GetItemDTORequest{
	private String itemId;
	private String companyId;

	public GetItemDTORequest() {}

	public GetItemDTORequest(String itemId, String companyId) {
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
