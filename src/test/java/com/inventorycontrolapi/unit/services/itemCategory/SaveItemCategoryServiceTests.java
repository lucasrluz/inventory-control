package com.inventorycontrolapi.unit.services.itemCategory;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.services.ItemCategoryService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;
import com.inventorycontrolapi.util.itemCategory.SaveItemCategoryDTORequestBuilder;

@ExtendWith(SpringExtension.class)
public class SaveItemCategoryServiceTests {
	@InjectMocks
	private ItemCategoryService itemCategoryService;

	@Mock
	private ItemCategoryRepository itemCategoryRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaItemCategoryId() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));
		
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		BDDMockito.when(this.itemCategoryRepository.save(ArgumentMatchers.any())).thenReturn(itemCategoryModelMock);

		// Test
		SaveItemCategoryDTORequest saveItemCategoryDTORequest = SaveItemCategoryDTORequestBuilder.createWithValidData();

		SaveItemCategoryDTOResponse saveItemCategoryDTOResponse = this.itemCategoryService.save(saveItemCategoryDTORequest);

		Assertions.assertThat(saveItemCategoryDTOResponse.getItemCategoryId()).isEqualTo(
			itemCategoryModelMock.getItemCategoryId().toString()
		);
	}
}
