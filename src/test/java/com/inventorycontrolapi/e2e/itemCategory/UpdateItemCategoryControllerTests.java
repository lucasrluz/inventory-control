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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorycontrolapi.dtos.itemCategory.UpdateItemCategoryDTORequest;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;
import com.inventorycontrolapi.util.itemCategory.UpdateItemCategoryDTORequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateItemCategoryControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ItemCategoryRepository itemCategoryRepository;

	@Autowired
	private CompanyRepository companyRepository;

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
	public void retorna200EInformacoesDaItemCategory() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		ItemCategoryModel itemCategoryModel = ItemCategoryModelBuilder.createWithItemCategoryId(companyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item-category/" + saveItemCategoryModel.getItemCategoryId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		String itemCategoryId = new JSONObject(response.getContentAsString()).getString("itemCategoryId");
		String name = new JSONObject(response.getContentAsString()).getString("name");

		Assertions.assertThat(itemCategoryId).isEqualTo(saveItemCategoryModel.getItemCategoryId().toString());
		Assertions.assertThat(name).isEqualTo(updateItemCategoryDTORequest.getName());

		Optional<ItemCategoryModel> findItemCategoryModelByItemCategoryId = this.itemCategoryRepository.findById(
			Long.parseLong(itemCategoryId)
		);

		Assertions.assertThat(findItemCategoryModelByItemCategoryId.get().getName()).isEqualTo(
			updateItemCategoryDTORequest.getName()
		);
	}

	@Test
	public void retorna200EInformacoesDaItemCategoryComNameJaUtilizadoPorOutraCompany() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		ItemCategoryModel itemCategoryModel = ItemCategoryModelBuilder.createWithItemCategoryId(companyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		CompanyModel companyModelB = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModelB.setCompanyId(111L);
		companyModelB.setName("Company B");
		companyModelB.setEmail("companyb@gmail.com");
		this.companyRepository.save(companyModelB);

		ItemCategoryModel itemCategoryModelB = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelB);
		itemCategoryModelB.setItemCategoryId(1L);
		itemCategoryModelB.setName("Item Category B");
		this.itemCategoryRepository.save(itemCategoryModelB);

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item-category/" + saveItemCategoryModel.getItemCategoryId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		String itemCategoryId = new JSONObject(response.getContentAsString()).getString("itemCategoryId");
		String name = new JSONObject(response.getContentAsString()).getString("name");

		Assertions.assertThat(itemCategoryId).isEqualTo(saveItemCategoryModel.getItemCategoryId().toString());
		Assertions.assertThat(name).isEqualTo(updateItemCategoryDTORequest.getName());

		Optional<ItemCategoryModel> findItemCategoryModelByItemCategoryId = this.itemCategoryRepository.findById(
			Long.parseLong(itemCategoryId)
		);

		Assertions.assertThat(findItemCategoryModelByItemCategoryId.get().getName()).isEqualTo(
			updateItemCategoryDTORequest.getName()
		);
	}
	
	@Test
	public void retorna404_ItemCategoryNaoEncontrado_ItemCategotyNaoCadastrado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item-category/0")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item Category");
	}
	
	@Test
	public void retorna404_ItemCategoryNaoEncontrado_CompanyDoItemCategoryDiferenteDoInformado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		CompanyModel companyModelForItemCategoryModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModelForItemCategoryModel.setCompanyId(1L);
		companyModelForItemCategoryModel.setName("Company B");
		companyModelForItemCategoryModel.setEmail("companyb@gmail.com");
		
		this.companyRepository.save(companyModelForItemCategoryModel);

		ItemCategoryModel itemCategoryModel = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelForItemCategoryModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item-category/" + saveItemCategoryModel.getItemCategoryId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item Category");
	}

	@Test
	public void retorna400_NameJaUtilizado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		ItemCategoryModel itemCategoryModel = ItemCategoryModelBuilder.createWithItemCategoryId(companyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemCategoryModel itemCategoryModelB = ItemCategoryModelBuilder.createWithItemCategoryId(companyModel);
		itemCategoryModelB.setItemCategoryId(1L);
		itemCategoryModelB.setName("Item Category B");
		this.itemCategoryRepository.save(itemCategoryModelB);

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item-category/" + saveItemCategoryModel.getItemCategoryId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Name already registered");
	}

	@Test
	public void retorna400_NameInvalido() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		ItemCategoryModel itemCategoryModel = ItemCategoryModelBuilder.createWithItemCategoryId(companyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithEmptyName();

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item-category/" + saveItemCategoryModel.getItemCategoryId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemCategoryDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("name: must not be blank");
	}
}
