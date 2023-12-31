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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;

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
public class GetItemControllerTests {
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

	@Test
	public void retorna200EListaComInformacoesDoItem() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			get("/api/item/" + saveItemModel.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		String itemId = new JSONObject(response.getContentAsString()).getString("itemId");
		String name = new JSONObject(response.getContentAsString()).getString("name");
		String unitPrice = new JSONObject(response.getContentAsString()).getString("unitPrice");
		String quantityInStock = new JSONObject(response.getContentAsString()).getString("quantityInStock");
		String itemCategory = new JSONObject(response.getContentAsString()).getString("itemCategory");

		Assertions.assertThat(itemId).isEqualTo(saveItemModel.getItemId().toString());
		Assertions.assertThat(name).isEqualTo(saveItemModel.getName());
		Assertions.assertThat(unitPrice).isEqualTo(String.valueOf(saveItemModel.getUnitPrice()));
		Assertions.assertThat(quantityInStock).isEqualTo(String.valueOf(saveItemModel.getQuantityInStock()));
		Assertions.assertThat(itemCategory).isEqualTo(saveItemModel.getItemCategoryModel().getName());
	}

	@Test
	public void retorna404_ItemNaoCadastrado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			get("/api/item/" + "0")
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item");
	}

	@Test
	public void retorna404_ItemComCompanyDiferenteDoInformado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		CompanyModel companyModelForItem = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModelForItem.setCompanyId(1L);
		companyModelForItem.setEmail("companyb@gmail.com");
		CompanyModel saveCompanyModelForItem = this.companyRepository.save(companyModelForItem);

		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModelForItem);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		ItemModel itemModel = new ItemModel("Item A", 1.11, 1, saveCompanyModelForItem, saveItemCategoryModel);
		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			get("/api/item/" + saveItemModel.getItemId().toString())
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("Not found Item");
	}
}
