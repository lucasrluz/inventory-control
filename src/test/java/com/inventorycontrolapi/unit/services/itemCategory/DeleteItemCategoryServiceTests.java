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

import com.inventorycontrolapi.dtos.itemCategory.DeleteItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.DeleteItemCategoryDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.services.ItemCategoryService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.DeleteItemCategoryDTORequestBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;

@ExtendWith(SpringExtension.class)
public class DeleteItemCategoryServiceTests {
	@InjectMocks
	private ItemCategoryService itemCategoryService;

	@Mock
	private ItemCategoryRepository itemCategoryRepository;

	@Test
	public void retornaItemCategoryId() throws Exception {
		// Mocks
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemCategoryModelMock));

		// Test
		DeleteItemCategoryDTORequest deleteItemCategoryDTORequest = DeleteItemCategoryDTORequestBuilder.createWithValidData();

		DeleteItemCategoryDTOResponse deleteItemCategoryDTOResponse = this.itemCategoryService.delete(deleteItemCategoryDTORequest);

		Assertions.assertThat(deleteItemCategoryDTOResponse.getItemCategoryId()).isEqualTo(itemCategoryModelMock.getItemCategoryId().toString());
	}
}
