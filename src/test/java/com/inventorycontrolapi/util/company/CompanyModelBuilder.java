package com.inventorycontrolapi.util.company;

import com.inventorycontrolapi.models.CompanyModel;

import at.favre.lib.crypto.bcrypt.BCrypt;

public abstract class CompanyModelBuilder {
	public static CompanyModel createWithCompanyIdAndHashPassword() {
		return new CompanyModel(
			0L,
			"Company A",
			"companya@gmail.com",
			BCrypt.withDefaults().hashToString(12, "123".toCharArray())
		);
	}
}
