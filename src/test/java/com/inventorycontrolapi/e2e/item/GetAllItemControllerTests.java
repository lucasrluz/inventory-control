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
public class GetAllItemControllerTests {
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

		ItemModel itemModelA = new ItemModel("Item A", 1.11, 1, saveCompanyModel, saveItemCategoryModel);
		ItemModel itemModelB = new ItemModel("Item B", 2.22, 2, saveCompanyModel, saveItemCategoryModel);
		ItemModel itemModelC = new ItemModel("Item C", 3.33, 3, saveCompanyModel, saveItemCategoryModel);

		ItemModel saveItemModelA = this.itemRepository.save(itemModelA);
		ItemModel saveItemModelB = this.itemRepository.save(itemModelB);
		ItemModel saveItemModelC = this.itemRepository.save(itemModelC);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			get("/api/item")
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		JSONArray responseJSONArray = new JSONArray(response.getContentAsString());

		JSONArray jsonArray = new JSONArray();

		JSONObject jsonObjectA = new JSONObject();
		JSONObject jsonObjectB = new JSONObject();
		JSONObject jsonObjectC = new JSONObject();

		jsonObjectA.put("itemId", saveItemModelA.getItemId().toString());
		jsonObjectA.put("name", saveItemModelA.getName());
		jsonObjectA.put("unitPrice", String.valueOf(saveItemModelA.getUnitPrice()));
		jsonObjectA.put("quantityInStock", String.valueOf(saveItemModelA.getQuantityInStock()));
		jsonObjectA.put("itemCategory", saveItemModelA.getItemCategoryModel().getName());

		jsonObjectB.put("itemId", saveItemModelB.getItemId().toString());
		jsonObjectB.put("name", saveItemModelB.getName());
		jsonObjectB.put("unitPrice", String.valueOf(saveItemModelB.getUnitPrice()));
		jsonObjectB.put("quantityInStock", String.valueOf(saveItemModelB.getQuantityInStock()));
		jsonObjectB.put("itemCategory", saveItemModelB.getItemCategoryModel().getName());

		jsonObjectC.put("itemId", saveItemModelC.getItemId().toString());
		jsonObjectC.put("name", saveItemModelC.getName());
		jsonObjectC.put("unitPrice", String.valueOf(saveItemModelC.getUnitPrice()));
		jsonObjectC.put("quantityInStock", String.valueOf(saveItemModelC.getQuantityInStock()));
		jsonObjectC.put("itemCategory", saveItemModelC.getItemCategoryModel().getName());

		jsonArray.put(jsonObjectA);
		jsonArray.put(jsonObjectB);
		jsonArray.put(jsonObjectC);

		Assertions.assertThat(responseJSONArray.toString().equals(jsonArray.toString())).isEqualTo(true);
	}

	@Test
	public void retorna200EListaVazia() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			get("/api/item")
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("[]");
	}
}
