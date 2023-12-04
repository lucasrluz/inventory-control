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

import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.UpdateCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.UpdateCompanyDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.CompanyService;
import com.inventorycontrolapi.services.exceptions.EmailAlreadyRegisteredException;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.company.SignUpCompanyDTORequestBuilder;
import com.inventorycontrolapi.util.company.UpdateCompanyDTORequestBuilder;

@ExtendWith(SpringExtension.class)
public class UpdateCompanyServiceTests {
	@InjectMocks
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaInformacoesDaCompany() throws Exception {
		// Mock
		BDDMockito.when(this.companyRepository.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.empty());

		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		BDDMockito.when(this.companyRepository.save(ArgumentMatchers.any())).thenReturn(companyModelMock);
		
		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithValidData();

		UpdateCompanyDTOResponse updateCompanyDTOResponse = this.companyService.update(updateCompanyDTORequest);

		Assertions.assertThat(updateCompanyDTOResponse.getCompanyId()).isEqualTo(companyModelMock.getCompanyId().toString());
	}

	@Test
	public void retornaException_EmailJaCadastrado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		BDDMockito.when(this.companyRepository.findByEmail(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));

		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithValidData();

		Assertions.assertThatExceptionOfType(EmailAlreadyRegisteredException.class)
			.isThrownBy(() -> this.companyService.update(updateCompanyDTORequest))
			.withMessage("Email already registered");
	}
}
