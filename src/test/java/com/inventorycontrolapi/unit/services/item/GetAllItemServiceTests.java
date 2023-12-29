package com.inventorycontrolapi.unit.services.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.dtos.item.GetAllItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetAllItemDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.ItemService;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;

import at.favre.lib.crypto.bcrypt.BCrypt;

@ExtendWith(SpringExtension.class)
public class GetAllItemServiceTests {
	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaUmaListaComAsInformacoesDaItem() {
		// Mock
		CompanyModel companyModelMock = new CompanyModel(
			UUID.randomUUID(),
			"Company A",
			"companya@gmail.com",
			BCrypt.withDefaults().hashToString(12, "123".toCharArray())
		);

		BDDMockito
			.when(this.companyRepository.findById(ArgumentMatchers.any()))
			.thenReturn(Optional.of(companyModelMock));
		
		ItemCategoryModel itemCategoryModelMock = new ItemCategoryModel(
			0L,
			"Item Category A",
			companyModelMock
		);

		ItemModel itemModelAMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);
		ItemModel itemModelBMock = new ItemModel(1L, "Item B", 2.22, 2, companyModelMock, itemCategoryModelMock);
		ItemModel itemModelCMock = new ItemModel(2L, "Item C", 3.33, 3, companyModelMock, itemCategoryModelMock);

		List<ItemModel> itemModels = new ArrayList<>();

		itemModels.add(itemModelAMock);
		itemModels.add(itemModelBMock);
		itemModels.add(itemModelCMock);

		BDDMockito
			.when(this.itemRepository.findByCompanyModel(ArgumentMatchers.any()))
			.thenReturn(itemModels);

		// Test
		GetAllItemDTORequest getAllItemDTORequest = new GetAllItemDTORequest(
			companyModelMock.getCompanyId().toString()
		);

		List<GetAllItemDTOResponse> getAllItemDTOResponse = this.itemService.getAll(getAllItemDTORequest);

		Assertions
			.assertThat(getAllItemDTOResponse.get(0).getItemId())
			.isEqualTo(itemModelAMock.getItemId().toString());
		Assertions
			.assertThat(getAllItemDTOResponse.get(0).getName())
			.isEqualTo(itemModelAMock.getName());
		Assertions
			.assertThat(getAllItemDTOResponse.get(0).getUnitPrice())
			.isEqualTo(String.valueOf(itemModelAMock.getUnitPrice()));
		Assertions
			.assertThat(getAllItemDTOResponse.get(0).getQuantityInStock())
			.isEqualTo(String.valueOf(itemModelAMock.getQuantityInStock()));
		Assertions
			.assertThat(getAllItemDTOResponse.get(0).getItemCategory())
			.isEqualTo(itemModelAMock.getItemCategoryModel().getName());

		Assertions
			.assertThat(getAllItemDTOResponse.get(1).getItemId())
			.isEqualTo(itemModelBMock.getItemId().toString());
		Assertions
			.assertThat(getAllItemDTOResponse.get(1).getName())
			.isEqualTo(itemModelBMock.getName());
		Assertions
			.assertThat(getAllItemDTOResponse.get(1).getUnitPrice())
			.isEqualTo(String.valueOf(itemModelBMock.getUnitPrice()));
		Assertions
			.assertThat(getAllItemDTOResponse.get(1).getQuantityInStock())
			.isEqualTo(String.valueOf(itemModelBMock.getQuantityInStock()));
		Assertions
			.assertThat(getAllItemDTOResponse.get(1).getItemCategory())
			.isEqualTo(itemModelBMock.getItemCategoryModel().getName());

		Assertions
			.assertThat(getAllItemDTOResponse.get(2).getItemId())
			.isEqualTo(itemModelCMock.getItemId().toString());
		Assertions
			.assertThat(getAllItemDTOResponse.get(2).getName())
			.isEqualTo(itemModelCMock.getName());
		Assertions
			.assertThat(getAllItemDTOResponse.get(2).getUnitPrice())
			.isEqualTo(String.valueOf(itemModelCMock.getUnitPrice()));
		Assertions
			.assertThat(getAllItemDTOResponse.get(2).getQuantityInStock())
			.isEqualTo(String.valueOf(itemModelCMock.getQuantityInStock()));
		Assertions
			.assertThat(getAllItemDTOResponse.get(2).getItemCategory())
			.isEqualTo(itemModelCMock.getItemCategoryModel().getName());
	}

	@Test
	public void retornaUmaListaVazia() {
		// Mock
		CompanyModel companyModelMock = new CompanyModel(
			UUID.randomUUID(),
			"Company A",
			"companya@gmail.com",
			BCrypt.withDefaults().hashToString(12, "123".toCharArray())
		);

		BDDMockito
			.when(this.companyRepository.findById(ArgumentMatchers.any()))
			.thenReturn(Optional.of(companyModelMock));

		// Test
		GetAllItemDTORequest getAllItemDTORequest = new GetAllItemDTORequest(
			companyModelMock.getCompanyId().toString()
		);

		List<GetAllItemDTOResponse> getAllItemDTOResponse = this.itemService.getAll(getAllItemDTORequest);

		Assertions
			.assertThat(getAllItemDTOResponse.isEmpty())
			.isEqualTo(true);
	}
}
