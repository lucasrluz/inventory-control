package com.inventorycontrolapi.services.exceptions;

public class NotFoundItemException extends Exception {
	public NotFoundItemException() {
		super("Not found Item");
	}
}
