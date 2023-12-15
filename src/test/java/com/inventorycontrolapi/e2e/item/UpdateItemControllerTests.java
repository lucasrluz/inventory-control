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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorycontrolapi.dtos.item.UpdateItemDTORequest;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UpdateItemControllerTests {
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
	public void retorna200EItemId() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest("0", "0", "Item B", "2.22", "2", saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item/" + saveItemModel.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		String itemId = new JSONObject(response.getContentAsString()).getString("itemId");
		Assertions.assertThat(itemId).isEqualTo(saveItemModel.getItemId().toString());

		Optional<ItemModel> findItemModelById = this.itemRepository.findById(Long.parseLong(saveItemModel.getItemId().toString()));

		Assertions.assertThat(findItemModelById.get().getName()).isEqualTo("Item B");
		Assertions.assertThat(String.valueOf(findItemModelById.get().getUnitPrice())).isEqualTo("2.22");
		Assertions.assertThat(String.valueOf(findItemModelById.get().getQuantityInStock())).isEqualTo("2");
	}

	@Test
	public void retorna200EItemId_NameJaCadastradoPorOutraCompany() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		CompanyModel companyModelB = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModelB.setEmail("companyb@gmail.com");
		CompanyModel saveCompanyModelB = this.companyRepository.save(companyModelB);
		
		ItemCategoryModel itemCategoryModelB = new ItemCategoryModel("Item Category A", saveCompanyModelB);
		ItemCategoryModel saveItemCategoryModelB = this.itemCategoryRepository.save(itemCategoryModelB);

		ItemModel itemModelB = new ItemModel("Item B", 1.11, 1, saveCompanyModelB, saveItemCategoryModelB);
		this.itemRepository.save(itemModelB);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest("0", "0", "Item B", "2.22", "2", saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item/" + saveItemModel.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		String itemId = new JSONObject(response.getContentAsString()).getString("itemId");
		Assertions.assertThat(itemId).isEqualTo(saveItemModel.getItemId().toString());

		Optional<ItemModel> findItemModelById = this.itemRepository.findById(Long.parseLong(saveItemModel.getItemId().toString()));

		Assertions.assertThat(findItemModelById.get().getName()).isEqualTo("Item B");
		Assertions.assertThat(String.valueOf(findItemModelById.get().getUnitPrice())).isEqualTo("2.22");
		Assertions.assertThat(String.valueOf(findItemModelById.get().getQuantityInStock())).isEqualTo("2");
	}

	@Test
	public void retorna404_ItemNaoCadastrado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest("0", "0", "Item B", "2.22", "2", saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item/" + "0")
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item");
	}

	@Test
	public void retornaException_ItemDiferenteDoCompany() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		this.itemRepository.save(itemModel);

		CompanyModel companyModelB = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModelB.setEmail("companyb@gmail.com");
		CompanyModel saveCompanyModelB = this.companyRepository.save(companyModelB);
		
		ItemCategoryModel itemCategoryModelB = new ItemCategoryModel("Item Category A", saveCompanyModelB);
		ItemCategoryModel saveItemCategoryModelB = this.itemCategoryRepository.save(itemCategoryModelB);

		ItemModel itemModelB = new ItemModel("Item B", 1.11, 1, saveCompanyModelB, saveItemCategoryModelB);
		ItemModel saveItemModelB = this.itemRepository.save(itemModelB);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest("0", "0", "Item B", "2.22", "2", saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item/" + saveItemModelB.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item");
	}

	@Test
	public void retorna400_NameJaCadastrado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		ItemModel itemModelB = new ItemModel("Item B", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		this.itemRepository.save(itemModelB);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest("0", "0", "Item B", "2.22", "2", saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item/" + saveItemModel.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Name already registered");
	}

	@Test
	public void retorna404_ItemCategoryNaoCadastrado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest("0", "0", "Item B", "2.22", "2", "1");

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item/" + saveItemModel.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item Category");
	}	

	@Test
	public void retorna400_NameInvalido_ValorVazio() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest("0", "0", "", "2.22", "2", saveItemCategoryModel.getItemCategoryId().toString());

		MockHttpServletResponse response = this.mockMvc.perform(
			put("/api/item/" + saveItemModel.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
			.contentType("application/json")
			.content(asJsonString(updateItemDTORequest))
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(400);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("name: must not be blank");
	}	

}
