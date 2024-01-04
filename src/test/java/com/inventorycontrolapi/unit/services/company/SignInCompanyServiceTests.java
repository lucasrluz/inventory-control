package com.inventorycontrolapi.unit.services.company;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.dtos.company.SignInCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignInCompanyDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.CompanyService;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.services.exceptions.EmailOrPasswordInvalidException;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.company.SignInCompanyDTORequestBuilder;

@ExtendWith(SpringExtension.class)
public class SignInCompanyServiceTests {
	@InjectMocks
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private JwtService jwtService;

	@Test
	public void retornaJwt() throws Exception {
		// Mocks
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		BDDMockito.when(this.companyRepository.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.of(companyModel));
		BDDMockito.when(this.jwtService.generateJwt(ArgumentMatchers.any())).thenReturn("fake-jwt");

		// Test
		SignInCompanyDTORequest signInCompanyDTORequest = SignInCompanyDTORequestBuilder.createWithValidData();

		SignInCompanyDTOResponse signInCompanyDTOResponse = this.companyService.signIn(signInCompanyDTORequest);

		Assertions.assertThat(signInCompanyDTOResponse.getJwt()).isEqualTo("fake-jwt");
	}

	@Test
	public void retornaException_EmailNaoCadastrado() throws Exception {
		// Mocks
		BDDMockito.when(this.companyRepository.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.empty());

		// Test
		SignInCompanyDTORequest signInCompanyDTORequest = SignInCompanyDTORequestBuilder.createWithValidData();

		Assertions.assertThatExceptionOfType(EmailOrPasswordInvalidException.class)
			.isThrownBy(() -> this.companyService.signIn(signInCompanyDTORequest))
			.withMessage("Email or password invalid");
	}

	@Test
	public void retornaException_PasswordInvalida() throws Exception {
		// Mocks
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModel.setPassword("456");
		BDDMockito.when(this.companyRepository.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.of(companyModel));

		// Test
		SignInCompanyDTORequest signInCompanyDTORequest = SignInCompanyDTORequestBuilder.createWithValidData();

		Assertions.assertThatExceptionOfType(EmailOrPasswordInvalidException.class)
			.isThrownBy(() -> this.companyService.signIn(signInCompanyDTORequest))
			.withMessage("Email or password invalid");
	}
}
