package com.inventorycontrolapi.unit.services.item;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.inventorycontrolapi.dtos.item.UpdateItemDTORequest;
import com.inventorycontrolapi.dtos.item.UpdateItemDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.ItemService;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemException;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;

@ExtendWith(SpringExtension.class)
public class UpdateItemServiceTests {
	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private ItemCategoryRepository itemCategoryRepository;

	@Test
	public void retornaItemId_NameNaoCadastrado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		ItemModel itemModelMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMock));

		BDDMockito.when(this.itemRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.empty());

		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(itemCategoryModelMock)
		);

		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(companyModelMock)
		);

		ItemModel itemModelMockEdited = new ItemModel(0L, "Item B", 2.22, 2, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.save(ArgumentMatchers.any())).thenReturn(
			itemModelMockEdited	
		);

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest(
			"0",
			companyModelMock.getCompanyId().toString(),
			"Item B",
			"2.22",
			"2",
			"0"
		);

		UpdateItemDTOResponse updateItemDTOResponse = this.itemService.update(updateItemDTORequest);

		Assertions.assertThat(updateItemDTOResponse.getItemId()).isEqualTo(itemModelMock.getItemId().toString());
	}

	@Test
	public void retornaItemId_NameJaCadastradoEmOutraCompany() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		ItemModel itemModelMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMock));

		CompanyModel companyModelMockB = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		companyModelMockB.setCompanyId(1L);
		ItemCategoryModel itemCategoryModelMockB = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMockB);
		ItemModel itemModelMockB = new ItemModel(1L, "Item B", 1.11, 1, companyModelMockB, itemCategoryModelMockB);

		BDDMockito.when(this.itemRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMockB));

		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(itemCategoryModelMock)
		);

		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(companyModelMock)
		);

		ItemModel itemModelMockEdited = new ItemModel(0L, "Item B", 2.22, 2, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.save(ArgumentMatchers.any())).thenReturn(
			itemModelMockEdited	
		);

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest(
			"0",
			companyModelMock.getCompanyId().toString(),
			"Item B",
			"2.22",
			"2",
			"0"
		);

		UpdateItemDTOResponse updateItemDTOResponse = this.itemService.update(updateItemDTORequest);

		Assertions.assertThat(updateItemDTOResponse.getItemId()).isEqualTo(itemModelMock.getItemId().toString());
	}

	@Test
	public void retornaException_ItemNaoCadastrado() {
		// Mock
		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest(
			"0",
			"0",
			"Item B",
			"2.22",
			"2",
			"0"
		);

		Assertions.assertThatExceptionOfType(NotFoundItemException.class)
			.isThrownBy(() -> this.itemService.update(updateItemDTORequest))
			.withMessage("Not found Item");
	}

	@Test
	public void retornaException_ItemNaoCadastrado_CompanyDiferente() {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		ItemModel itemModelMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMock));

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest(
			"0",
			"1",
			"Item B",
			"2.22",
			"2",
			"0"
		);

		Assertions.assertThatExceptionOfType(NotFoundItemException.class)
			.isThrownBy(() -> this.itemService.update(updateItemDTORequest))
			.withMessage("Not found Item");
	}

	@Test
	public void retornaException_NameJaCadastrado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		ItemModel itemModelMockA = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMockA));

		ItemModel itemModelMockB = new ItemModel(1L, "Item B", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMockB));

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest(
			"0",
			companyModelMock.getCompanyId().toString(),
			"Item B",
			"2.22",
			"2",
			"0"
		);

		Assertions.assertThatExceptionOfType(NameAlreadyRegisteredException.class)
			.isThrownBy(() -> this.itemService.update(updateItemDTORequest))
			.withMessage("Name already registered");
	}

	@Test
	public void retornaException_ItemCategoryNaoEncontrado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		ItemModel itemModelMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMock));

		BDDMockito.when(this.itemRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.empty());

		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.empty()
		);

		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(
			Optional.of(companyModelMock)
		);

		ItemModel itemModelMockEdited = new ItemModel(0L, "Item B", 2.22, 2, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.save(ArgumentMatchers.any())).thenReturn(
			itemModelMockEdited	
		);

		// Test
		UpdateItemDTORequest updateItemDTORequest = new UpdateItemDTORequest(
			"0",
			companyModelMock.getCompanyId().toString(),
			"Item B",
			"2.22",
			"2",
			"0"
		);

		Assertions.assertThatExceptionOfType(NotFoundItemCategoryException.class)
			.isThrownBy(() -> this.itemService.update(updateItemDTORequest))
			.withMessage("Not found Item Category");
	}
}
