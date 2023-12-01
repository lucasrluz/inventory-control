package com.inventorycontrolapi.services.exceptions;

public class EmailAlreadyRegisteredException extends Exception {
	public EmailAlreadyRegisteredException() {
		super("Email already registered");
	}
}
