package com.inventorycontrolapi.domains;


import java.util.Set;

import com.inventorycontrolapi.domains.exceptions.InvalidItemCategoryDomainException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;

public class ItemCategoryDomain {
	@NotBlank
	private String name;

	public ItemCategoryDomain() {}

	public ItemCategoryDomain(@NotBlank String name) {
		this.name = name;
	}

    public static ItemCategoryDomain validate(String name) throws InvalidItemCategoryDomainException {
        try {
            ItemCategoryDomain itemCategoryDomain = new ItemCategoryDomain(name);

            validation(itemCategoryDomain);

            return itemCategoryDomain;
        } catch (ConstraintViolationException exception) {
            String message = exception.getMessage();

            throw new InvalidItemCategoryDomainException(message);
        }
    }

    private static void validation(ItemCategoryDomain itemCategoryDomain) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<ItemCategoryDomain>> constraintViolations = validator.validate(itemCategoryDomain);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
