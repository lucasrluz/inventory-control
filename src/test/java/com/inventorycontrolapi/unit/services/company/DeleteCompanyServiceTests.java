package com.inventorycontrolapi.unit.services.company;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.dtos.company.DeleteCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.DeleteCompanyDTOResponse;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.CompanyService;
import com.inventorycontrolapi.util.company.DeleteCompanyDTORequestBuilder;

@ExtendWith(SpringExtension.class)
public class DeleteCompanyServiceTests {
	@InjectMocks
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaCompanyId() {
		DeleteCompanyDTORequest deleteCompanyDTORequest = DeleteCompanyDTORequestBuilder.createWithValidData();

		DeleteCompanyDTOResponse deleteCompanyDTOResponse = this.companyService.delete(deleteCompanyDTORequest);

		Assertions.assertThat(deleteCompanyDTOResponse.getCompanyId()).isEqualTo(deleteCompanyDTORequest.getCompanyId());
	}
}
