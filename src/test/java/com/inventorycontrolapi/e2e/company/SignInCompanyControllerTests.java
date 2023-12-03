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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorycontrolapi.dtos.company.SignInCompanyDTORequest;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.company.SignInCompanyDTORequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignInCompanyControllerTests {
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
	public void retorna201EJwt() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		// Test
		SignInCompanyDTORequest signInCompanyDTORequest = SignInCompanyDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/company/auth/signin")
			.contentType("application/json")
			.content(asJsonString(signInCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(201);

		String jwt = new JSONObject(response.getContentAsString()).getString("jwt");
		String companyId = this.jwtService.validateJwt(jwt);

		Assertions.assertThat(companyId).isEqualTo(saveCompanyModel.getCompanyId().toString());
	}

	@Test
	public void retorna400EMensagemDeErro_EmailNaoCadastrado() throws Exception {
		// Test
		SignInCompanyDTORequest signInCompanyDTORequest = SignInCompanyDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/company/auth/signin")
			.contentType("application/json")
			.content(asJsonString(signInCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Email or password invalid");
	}

	@Test
	public void retorna400EMensagemDeErro_PasswordInvalida() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		this.companyRepository.save(companyModel);

		// Test
		SignInCompanyDTORequest signInCompanyDTORequest = SignInCompanyDTORequestBuilder.createWithValidData();
		signInCompanyDTORequest.setPassword("456");

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/company/auth/signin")
			.contentType("application/json")
			.content(asJsonString(signInCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Email or password invalid");
	}
}
