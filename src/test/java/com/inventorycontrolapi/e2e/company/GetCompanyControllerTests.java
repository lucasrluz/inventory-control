package com.inventorycontrolapi.e2e.company;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorycontrolapi.dtos.company.GetCompanyDTORequest;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.company.GetCompanyDTORequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetCompanyControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private JwtService jwtService;

	@BeforeEach
	@AfterAll
	public void deleteAll() {
		this.companyRepository.deleteAll();
	}

	public String asJsonString(final Object obj) {
        try {
          return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception exception) {
          throw new RuntimeException(exception);
        }
	}

	@Test
	public void retorna201EInformacoesDaCompany() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		GetCompanyDTORequest getCompanyDTORequest = GetCompanyDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			get("/api/company")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(getCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		String companyId = new JSONObject(response.getContentAsString()).getString("companyId");
		String name = new JSONObject(response.getContentAsString()).getString("name");
		String email = new JSONObject(response.getContentAsString()).getString("email");

		Assertions.assertThat(companyId).isEqualTo(saveCompanyModel.getCompanyId().toString());
		Assertions.assertThat(name).isEqualTo(saveCompanyModel.getName());
		Assertions.assertThat(email).isEqualTo(saveCompanyModel.getEmail());
	}
}
