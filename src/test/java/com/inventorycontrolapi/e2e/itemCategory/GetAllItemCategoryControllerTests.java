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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.json.JSONObject;

import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.services.JwtService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetAllItemCategoryControllerTests {
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

	@Test
	public void retorna200EListaDeItemCategory() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModelMockA = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel itemCategoryModelMockB = new ItemCategoryModel("Item Category B", saveCompanyModel);
		ItemCategoryModel itemCategoryModelMockC = new ItemCategoryModel("Item Category C", saveCompanyModel);	

		ItemCategoryModel saveItemCategoryModelA = this.itemCategoryRepository.save(itemCategoryModelMockA);
		ItemCategoryModel saveItemCategoryModelB = this.itemCategoryRepository.save(itemCategoryModelMockB);
		ItemCategoryModel saveItemCategoryModelC = this.itemCategoryRepository.save(itemCategoryModelMockC);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			get("/api/item-category")
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		JSONArray responseJSONArray = new JSONArray(response.getContentAsString());

		JSONArray jsonArray = new JSONArray();

		JSONObject jsonObjectA = new JSONObject();
		JSONObject jsonObjectB = new JSONObject();
		JSONObject jsonObjectC = new JSONObject();

		jsonObjectA.put("itemCategoryId", saveItemCategoryModelA.getItemCategoryId().toString());
		jsonObjectA.put("name", saveItemCategoryModelA.getName());
	
		jsonObjectB.put("itemCategoryId", saveItemCategoryModelB.getItemCategoryId().toString());
		jsonObjectB.put("name", saveItemCategoryModelB.getName());		

		jsonObjectC.put("itemCategoryId", saveItemCategoryModelC.getItemCategoryId().toString());
		jsonObjectC.put("name", saveItemCategoryModelC.getName());

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
			get("/api/item-category")
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);
		Assertions.assertThat(response.getContentAsString()).isEqualTo("[]");
	}
}
