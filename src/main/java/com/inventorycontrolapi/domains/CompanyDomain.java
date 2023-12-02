package com.inventorycontrolapi.domains;

import java.util.Set;

import com.inventorycontrolapi.domains.exceptions.InvalidCompanyDomainException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CompanyDomain {
	@NotBlank(message = "The name cannot be empty")
	private String name;

	@NotBlank(message = "The email cannot be empty")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "The password cannot be empty")
	private String password;

	private CompanyDomain(@NotBlank String name, @NotBlank @Email String email, @NotBlank String password) {
        this.name = name;
		this.email = email;
		this.password = password;
    }

    public static CompanyDomain validate(String name, String email, String password) throws InvalidCompanyDomainException {
        try {
            CompanyDomain companyDomain = new CompanyDomain(name, email, password);

            validation(companyDomain);

            return companyDomain;
        } catch (ConstraintViolationException exception) {
            String message = exception.getMessage();

            throw new InvalidCompanyDomainException(message);
        }
    }

    private static void validation(CompanyDomain companyDomain) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();

        Set<ConstraintViolation<CompanyDomain>> constraintViolations = validator.validate(companyDomain);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
