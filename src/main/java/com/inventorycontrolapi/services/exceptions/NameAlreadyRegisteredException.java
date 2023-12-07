package com.inventorycontrolapi.services.exceptions;

public class NameAlreadyRegisteredException extends Exception {
	public NameAlreadyRegisteredException() {
		super("Name already registered");
	}
}
