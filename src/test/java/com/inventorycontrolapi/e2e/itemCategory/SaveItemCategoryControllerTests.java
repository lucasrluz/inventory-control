package com.inventorycontrolapi.e2e.itemCategory;

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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.SaveItemCategoryDTORequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveItemCategoryControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ItemCategoryRepository itemCategoryRepository;

	@Autowired
	private JwtService jwtService;

	@BeforeEach
	@AfterAll
	public void deleteAll() {
		this.itemCategoryRepository.deleteAll();
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
	public void retorna201EItemCategoryId() throws Exception {
		// Environment data
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModelMock);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		SaveItemCategoryDTORequest saveItemCategoryDTORequest = SaveItemCategoryDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/item-category")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(saveItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(201);

		String itemCategoryId = new JSONObject(response.getContentAsString()).getString("itemCategoryId");

		Optional<ItemCategoryModel> findItemCategoryModelByItemCategoryId = this.itemCategoryRepository.findById(
			Long.parseLong(itemCategoryId)
		);

		Assertions.assertThat(findItemCategoryModelByItemCategoryId.isEmpty()).isEqualTo(false);
		Assertions.assertThat(findItemCategoryModelByItemCategoryId.get().getName()).isEqualTo(
			saveItemCategoryDTORequest.getName()
		);
		Assertions.assertThat(findItemCategoryModelByItemCategoryId.get().getCompanyModel().getCompanyId().toString()).isEqualTo(
			saveCompanyModel.getCompanyId().toString()
		);
	}
	
	@Test
	public void retorna400EMensagemDeErro_NameComValorVazio() throws Exception {
		// Environment data
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModelMock);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		SaveItemCategoryDTORequest saveItemCategoryDTORequest = SaveItemCategoryDTORequestBuilder.createWithEmptyName();

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/item-category")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(saveItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("name: must not be blank");
	}
}
