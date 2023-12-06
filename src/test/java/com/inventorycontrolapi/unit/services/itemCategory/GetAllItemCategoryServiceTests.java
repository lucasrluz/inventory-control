package com.inventorycontrolapi.unit.services.itemCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.services.ItemCategoryService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.GetAllItemCategoryDTORequestBuilder;

@ExtendWith(SpringExtension.class)
public class GetAllItemCategoryServiceTests {
	@InjectMocks
	private ItemCategoryService itemCategoryService;

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private ItemCategoryRepository itemCategoryRepository;

	@Test
	public void retornaUmListaDeItemCategory() {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));

		ItemCategoryModel itemCategoryModelMockA = new ItemCategoryModel(0L, "Item Category A", companyModelMock);
		ItemCategoryModel itemCategoryModelMockB = new ItemCategoryModel(1L, "Item Category B", companyModelMock);
		ItemCategoryModel itemCategoryModelMockC = new ItemCategoryModel(2L, "Item Category C", companyModelMock);

		List<ItemCategoryModel> itemCategoryModels = new ArrayList<>();
		itemCategoryModels.add(itemCategoryModelMockA);
		itemCategoryModels.add(itemCategoryModelMockB);
		itemCategoryModels.add(itemCategoryModelMockC);

		BDDMockito.when(this.itemCategoryRepository.findByCompanyModel(ArgumentMatchers.any())).thenReturn(itemCategoryModels);

		// Test
		GetAllItemCategoryDTORequest getAllItemCategoryDTORequest = GetAllItemCategoryDTORequestBuilder.createWithValidData();

		List<GetAllItemCategoryDTOResponse> getAllItemCategoryDTOResponse = this.itemCategoryService.getAll(
			getAllItemCategoryDTORequest
		);

		Assertions.assertThat(getAllItemCategoryDTOResponse.get(0).getItemCategoryId()).isEqualTo("0");
		Assertions.assertThat(getAllItemCategoryDTOResponse.get(0).getName()).isEqualTo("Item Category A");

		Assertions.assertThat(getAllItemCategoryDTOResponse.get(1).getItemCategoryId()).isEqualTo("1");
		Assertions.assertThat(getAllItemCategoryDTOResponse.get(1).getName()).isEqualTo("Item Category B");

		Assertions.assertThat(getAllItemCategoryDTOResponse.get(2).getItemCategoryId()).isEqualTo("2");
		Assertions.assertThat(getAllItemCategoryDTOResponse.get(2).getName()).isEqualTo("Item Category C");
	}
	
	@Test
	public void retornaUmListaVazia() {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));

		List<ItemCategoryModel> itemCategoryModels = new ArrayList<>();

		BDDMockito.when(this.itemCategoryRepository.findByCompanyModel(ArgumentMatchers.any())).thenReturn(itemCategoryModels);

		// Test
		GetAllItemCategoryDTORequest getAllItemCategoryDTORequest = GetAllItemCategoryDTORequestBuilder.createWithValidData();

		List<GetAllItemCategoryDTOResponse> getAllItemCategoryDTOResponse = this.itemCategoryService.getAll(
			getAllItemCategoryDTORequest
		);

		Assertions.assertThat(getAllItemCategoryDTOResponse.isEmpty()).isEqualTo(true);
	}
}
