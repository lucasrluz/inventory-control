package com.inventorycontrolapi.unit.services.company;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.CompanyService;
import com.inventorycontrolapi.services.exceptions.EmailAlreadyRegisteredException;

import at.favre.lib.crypto.bcrypt.BCrypt;

@ExtendWith(SpringExtension.class)
public class SignUpCompanyServiceTests {
	@InjectMocks
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaCompanyId() throws Exception {
		// Mock
		BDDMockito
			.when(this.companyRepository.findByEmail(ArgumentMatchers.any()))
			.thenReturn(Optional.empty());

		CompanyModel companyModelMock = new CompanyModel(
			UUID.randomUUID(),
			"Company A",
			"companya@gmail.com",
			BCrypt.withDefaults().hashToString(12, "123".toCharArray())
		);

		BDDMockito
			.when(this.companyRepository.save(ArgumentMatchers.any()))
			.thenReturn(companyModelMock);

		// Test
		SignUpCompanyDTORequest signUpCompanyDTORequest = new SignUpCompanyDTORequest(
			"Company A",
			"companya@gmail.com",
			"123"
		);

		SignUpCompanyDTOResponse signUpCompanyDTOResponse = this.companyService.signUp(signUpCompanyDTORequest);

		Assertions
			.assertThat(signUpCompanyDTOResponse.getCompanyId())
			.isEqualTo(companyModelMock.getCompanyId().toString());
	}

	@Test
	public void retornaException_EmailJaCadastrado() throws Exception {
		// Mock
		CompanyModel companyModelMock = new CompanyModel(
			UUID.randomUUID(),
			"Company A",
			"companya@gmail.com",
			BCrypt.withDefaults().hashToString(12, "123".toCharArray())
		);

		BDDMockito
			.when(this.companyRepository.findByEmail(ArgumentMatchers.any()))
			.thenReturn(Optional.of(companyModelMock));

		// Test
		SignUpCompanyDTORequest signUpCompanyDTORequest = new SignUpCompanyDTORequest(
			"Company A",
			"companya@gmail.com",
			"123"
		);

		Assertions
			.assertThatExceptionOfType(EmailAlreadyRegisteredException.class)
			.isThrownBy(() -> this.companyService.signUp(signUpCompanyDTORequest))
			.withMessage("Email already registered");
	}
}
