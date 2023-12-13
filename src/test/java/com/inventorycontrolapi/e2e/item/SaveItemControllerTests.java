package com.inventorycontrolapi.e2e.item;

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
import com.inventorycontrolapi.dtos.item.SaveItemDTORequest;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.item.ItemModelBuilder;
import com.inventorycontrolapi.util.item.SaveItemDTORequestBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveItemControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private ItemCategoryRepository itemCategoryRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private JwtService jwtService;

	@BeforeEach
	@AfterAll
	public void deleteAll() {
		this.itemRepository.deleteAll();
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
	public void retorna201EItemId() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithValidData();
		saveItemDTORequest.setItemCategoryId(saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/item")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(saveItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(201);

		String itemId = new JSONObject(response.getContentAsString()).getString("itemId");

		Optional<ItemModel> findItemModelByItemId = this.itemRepository.findById(Long.parseLong(itemId));

		Assertions.assertThat(findItemModelByItemId.isEmpty()).isEqualTo(false);
		Assertions.assertThat(findItemModelByItemId.get().getName()).isEqualTo("Item A");
		Assertions.assertThat(findItemModelByItemId.get().getUnitPrice()).isEqualTo(1.25);
		Assertions.assertThat(findItemModelByItemId.get().getQuantityInStock()).isEqualTo(1);
		Assertions.assertThat(findItemModelByItemId.get().getCompanyModel().getCompanyId().toString()).isEqualTo(saveCompanyModel.getCompanyId().toString());
		Assertions.assertThat(findItemModelByItemId.get().getItemCategoryModel().getItemCategoryId().toString()).isEqualTo(saveItemCategoryModel.getItemCategoryId().toString());
	}

	@Test
	public void retorna404_ItemCategoryNaoCadastrada() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithValidData();

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/item")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(saveItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);

		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item Category");
	}

	@Test
	public void retorna400_NameJaCadastrado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = ItemModelBuilder.createWithItemId(companyModel, itemCategoryModel);
		this.itemRepository.save(itemModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithValidData();
		saveItemDTORequest.setItemCategoryId(saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/item")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(saveItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Name already registered");
	}

	@Test
	public void retorna400_NameComValorVazio() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithEmptyName();

		MockHttpServletResponse response = this.mockMvc.perform(
			post("/api/item")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(saveItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("name: must not be blank");
	}
}
