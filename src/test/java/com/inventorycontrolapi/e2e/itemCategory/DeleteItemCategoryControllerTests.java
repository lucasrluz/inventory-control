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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.Optional;

import org.assertj.core.api.Assertions;
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
public class DeleteItemCategoryControllerTests {
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
	public void retorna200EItemCategoryId() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			delete("/api/item-category/" + saveItemCategoryModel.getItemCategoryId().toString())
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(200);

		String itemCategoryId = new JSONObject(response.getContentAsString()).getString("itemCategoryId");

		Assertions.assertThat(itemCategoryId).isEqualTo(itemCategoryModel.getItemCategoryId().toString());

		Optional<ItemCategoryModel> findItemCategoryModelByItemCategoryId = this.itemCategoryRepository.findById(Long.parseLong(itemCategoryId));

		Assertions.assertThat(findItemCategoryModelByItemCategoryId.isEmpty()).isEqualTo(true);
	}

	@Test
	public void retorna404_ItemCategoryNaoEncontrado() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			delete("/api/item-category/" + "0")
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
	}

	@Test
	public void retorna404_CompanyDiferenteDaCompanyCadastrada() throws Exception {
		// Environment data
		CompanyModel companyModel = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);
		
		ItemCategoryModel itemCategoryModel = new ItemCategoryModel("Item Category A", saveCompanyModel);
		this.itemCategoryRepository.save(itemCategoryModel);
		
		CompanyModel companyModelB = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModelB.setName("Company B");
		companyModelB.setEmail("companyb@gmail.com");
		CompanyModel saveCompanyModelB = this.companyRepository.save(companyModelB);

		ItemCategoryModel itemCategoryModelB = new ItemCategoryModel("Item Category B", saveCompanyModelB);
		ItemCategoryModel saveItemCategoryModelB = this.itemCategoryRepository.save(itemCategoryModelB);

		String jwt = this.jwtService.generateJwt(saveCompanyModel.getCompanyId().toString());

		// Test
		MockHttpServletResponse response = this.mockMvc.perform(
			delete("/api/item-category/" + saveItemCategoryModelB.getItemCategoryId().toString())
			.header("Authorization", "Bearer " + jwt)
		).andReturn().getResponse();

		Assertions.assertThat(response.getStatus()).isEqualTo(404);
	}
}
