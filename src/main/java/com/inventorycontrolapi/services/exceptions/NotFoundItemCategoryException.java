package com.inventorycontrolapi.services.exceptions;

public class NotFoundItemCategoryException extends Exception {
	public NotFoundItemCategoryException() {
		super("Not found Item Category");
	}
}
