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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.UpdateCompanyDTORequest;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.company.SignUpCompanyDTORequestBuilder;
import com.inventorycontrolapi.util.company.UpdateCompanyDTORequestBuilder;

import at.favre.lib.crypto.bcrypt.BCrypt;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateCompanyControllerTests {
	@Autowired
	private MockMvc	mockMvc;

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
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/company")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(201);

		String companyId = new JSONObject(response.getContentAsString()).getString("companyId");
		String name = new JSONObject(response.getContentAsString()).getString("name");
		String email = new JSONObject(response.getContentAsString()).getString("email");

		Assertions.assertThat(companyId).isEqualTo(saveCompanyModel.getCompanyId().toString());
		Assertions.assertThat(name).isEqualTo(updateCompanyDTORequest.getName());
		Assertions.assertThat(email).isEqualTo(updateCompanyDTORequest.getEmail());

		Optional<CompanyModel> findCompanyModelByCompanyId = this.companyRepository.findById(UUID.fromString(companyId));

		Assertions.assertThat(findCompanyModelByCompanyId.isEmpty()).isEqualTo(false);
		Assertions.assertThat(findCompanyModelByCompanyId.get().getName()).isEqualTo("Company B");
		Assertions.assertThat(findCompanyModelByCompanyId.get().getEmail()).isEqualTo("companyb@gmail.com");
		Assertions.assertThat(
			BCrypt.verifyer().verify(
				updateCompanyDTORequest.getPassword().toCharArray(),
				findCompanyModelByCompanyId.get().getPassword()
			).verified
		).isEqualTo(true);
	}	
	
	@Test
	public void retorna400EMensagemDeErro_EmailJaCadastrado() throws Exception {
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();

		companyModel.setName("Company B");
		companyModel.setEmail("companyb@gmail.com");
		companyModel.setPassword("456");

		this.companyRepository.save(companyModel);

		CompanyModel saveCompanyModel = this.companyRepository.save(
			CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword()
		);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/company")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Email already registered");
	}

	@Test
	public void retorna400EMensagemDeErro_NameComValorVazio() throws Exception {
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithEmptyName();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/company")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("name: must not be blank");
	}	
	
	@Test
	public void retorna400EMensagemDeErro_EmailComFormatoInvalido() throws Exception {
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithInvalidEmailFormat();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/company")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("email: invalid format");
	}	

	@Test
	public void retorna400EMensagemDeErro_EmailComValorVazio() throws Exception {
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithEmptyEmail();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/company")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("email: must not be blank");
	}	

	@Test
	public void retorna400EMensagemDeErro_PasswordComValorVazio() throws Exception {
		CompanyModel companyModel = CompanyModelBuilder.createWithEmptyCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateCompanyDTORequest updateCompanyDTORequest = UpdateCompanyDTORequestBuilder.createWithEmptyPassword();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/company")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateCompanyDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("password: must not be blank");
	}		
}
