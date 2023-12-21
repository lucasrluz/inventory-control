package com.inventorycontrolapi.unit.services.item;

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

import com.inventorycontrolapi.dtos.item.SaveItemDTORequest;
import com.inventorycontrolapi.dtos.item.SaveItemDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.ItemService;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.item.ItemModelBuilder;
import com.inventorycontrolapi.util.item.SaveItemDTORequestBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;

@ExtendWith(SpringExtension.class)
public class SaveItemServiceTests {
	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private ItemCategoryRepository itemCategoryRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaItemId_ComNameNaoCadastrado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);

		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(itemCategoryModelMock)
		);

		BDDMockito.when(this.itemRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.empty());
		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));
		
		ItemModel itemModelMock = ItemModelBuilder.createWithItemId(companyModelMock, itemCategoryModelMock);
		BDDMockito.when(this.itemRepository.save(ArgumentMatchers.any())).thenReturn(itemModelMock);

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithValidData();

		SaveItemDTOResponse saveItemDTOResponse = this.itemService.save(saveItemDTORequest);

		Assertions.assertThat(saveItemDTOResponse.getItemId()).isEqualTo(itemModelMock.getItemId().toString());
	}

	@Test
	public void retornaItemId_ComNameCadastrado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);

		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(itemCategoryModelMock)
		);

		CompanyModel companyModelForItemMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelForItemMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelForItemMock);
		itemCategoryModelForItemMock.setItemCategoryId(1L);
		companyModelForItemMock.setCompanyId(UUID.randomUUID());
		ItemModel itemModelWithName = ItemModelBuilder.createWithItemId(companyModelForItemMock, itemCategoryModelForItemMock);

		BDDMockito.when(this.itemRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelWithName));

		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));
		
		ItemModel itemModelMock = ItemModelBuilder.createWithItemId(companyModelMock, itemCategoryModelMock);
		BDDMockito.when(this.itemRepository.save(ArgumentMatchers.any())).thenReturn(itemModelMock);

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithValidData();

		SaveItemDTOResponse saveItemDTOResponse = this.itemService.save(saveItemDTORequest);

		Assertions.assertThat(saveItemDTOResponse.getItemId()).isEqualTo(itemModelMock.getItemId().toString());
	}

	@Test
	public void retornaException_ItemCategoryNaoCadastrado() throws Exception {
		// Mock
		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.empty()
		);

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithValidData();

		Assertions.assertThatExceptionOfType(NotFoundItemCategoryException.class)
			.isThrownBy(() -> this.itemService.save(saveItemDTORequest))
			.withMessage("Not found Item Category");
	}
	
	@Test
	public void retornaException_NameJaUtilizado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);

		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(itemCategoryModelMock)
		);

		ItemModel itemModelBMock = ItemModelBuilder.createWithItemId(companyModelMock, itemCategoryModelMock);
		itemModelBMock.setItemId(1L);

		BDDMockito.when(this.itemRepository.findByName(ArgumentMatchers.any())).thenReturn(
			Optional.of(itemModelBMock)
		);

		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));
		
		ItemModel itemModelMock = ItemModelBuilder.createWithItemId(companyModelMock, itemCategoryModelMock);
		BDDMockito.when(this.itemRepository.save(ArgumentMatchers.any())).thenReturn(itemModelMock);

		// Test
		SaveItemDTORequest saveItemDTORequest = SaveItemDTORequestBuilder.createWithValidData();
		saveItemDTORequest.setCompanyId(companyModelMock.getCompanyId().toString());

		Assertions.assertThatExceptionOfType(NameAlreadyRegisteredException.class)
			.isThrownBy(() -> this.itemService.save(saveItemDTORequest))
			.withMessage("Name already registered");
	}
}
