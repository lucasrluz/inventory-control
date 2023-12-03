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

import com.inventorycontrolapi.dtos.company.GetCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.GetCompanyDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.CompanyService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.company.GetCompanyDTORequestBuilder;

@ExtendWith(SpringExtension.class)
public class GetCompanyServiceTests {
	@InjectMocks
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaInformacoesDaCompany() {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));

		// Test
		GetCompanyDTORequest getCompanyDTORequest = GetCompanyDTORequestBuilder.createWithValidData();

		GetCompanyDTOResponse getCompanyDTOResponse = this.companyService.get(getCompanyDTORequest);

		Assertions.assertThat(getCompanyDTOResponse.getCompanyId()).isEqualTo(getCompanyDTORequest.getCompanyId());
	}
}
