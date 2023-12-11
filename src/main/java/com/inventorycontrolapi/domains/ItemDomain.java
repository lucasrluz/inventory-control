package com.inventorycontrolapi.domains;

import java.util.Set;

import com.inventorycontrolapi.domains.exceptions.InvalidItemDomainException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotBlank;

public class ItemDomain {
	@NotBlank
	private String name;
	
	public ItemDomain() {}

	public ItemDomain(@NotBlank String name) {
		this.name = name;
	}

    public static ItemDomain validate(String name) throws InvalidItemDomainException {
        try {
            ItemDomain itemDomain = new ItemDomain(name);

            validation(itemDomain);

            return itemDomain;
        } catch (ConstraintViolationException exception) {
            String message = exception.getMessage();

            throw new InvalidItemDomainException(message);
        }
    }

    private static void validation(ItemDomain itemDomain) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<ItemDomain>> constraintViolations = validator.validate(itemDomain);

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
