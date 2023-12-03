package com.inventorycontrolapi.unit.domains;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.domains.CompanyDomain;
import com.inventorycontrolapi.domains.exceptions.InvalidCompanyDomainException;

@ExtendWith(SpringExtension.class)
public class CompanyDomainTests {
	@Test
	public void retornaCompanyDomain() throws Exception {
		CompanyDomain companyDomain = CompanyDomain.validate(
			"Company A",
			"companya@gmail.com",
			"123"
		);

		Assertions.assertThat(companyDomain.getName()).isEqualTo("Company A");
		Assertions.assertThat(companyDomain.getEmail()).isEqualTo("companya@gmail.com");
		Assertions.assertThat(companyDomain.getPassword()).isEqualTo("123");
	}
	
	@Test
	public void retornaException_NameComValorVazio() throws Exception {		
		Assertions.assertThatExceptionOfType(InvalidCompanyDomainException.class)
			.isThrownBy(() -> CompanyDomain.validate("", "companya@gmail.com", "123"))
			.withMessage("name: must not be blank");
	}
	
	@Test
	public void retornaException_EmailComValorVazio() throws Exception {		
		Assertions.assertThatExceptionOfType(InvalidCompanyDomainException.class)
			.isThrownBy(() -> CompanyDomain.validate("Company A", "", "123"))
			.withMessage("email: must not be blank");
	}

	@Test
	public void retornaException_EmailComFormatoInvalido() throws Exception {		
		Assertions.assertThatExceptionOfType(InvalidCompanyDomainException.class)
			.isThrownBy(() -> CompanyDomain.validate("Company A", "@gmail.com", "123"))
			.withMessage("email: invalid format");
	}

	@Test
	public void retornaException_PasswordComValorVazio() throws Exception {		
		Assertions.assertThatExceptionOfType(InvalidCompanyDomainException.class)
			.isThrownBy(() -> CompanyDomain.validate("Company A", "companya@gmail.com", ""))
			.withMessage("password: must not be blank");
	}
}
