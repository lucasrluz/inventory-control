package com.inventorycontrolapi.util.company;

import java.util.UUID;

import com.inventorycontrolapi.models.CompanyModel;

import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class CompanyModelBuilder {
	public static CompanyModel createWithCompanyIdAndHashPassword() {
		return new CompanyModel(
			UUID.randomUUID(),
			"Company A",
			"companya@gmail.com",
			BCrypt.withDefaults().hashToString(12, "123".toCharArray())
		);
	}

	public static CompanyModel createWithEmptyCompanyIdAndHashPassword() {
		return new CompanyModel(
			"Company A",
			"companya@gmail.com",
			BCrypt.withDefaults().hashToString(12, "123".toCharArray())
		);
	}
}
