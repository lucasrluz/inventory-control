package com.inventorycontrolapi.services.exceptions;

public class EmailOrPasswordInvalidException extends Exception {
	public EmailOrPasswordInvalidException() {
		super("Email or password invalid");
	}
}
