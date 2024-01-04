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

import com.inventorycontrolapi.dtos.item.DeleteItemDTORequest;
import com.inventorycontrolapi.dtos.item.DeleteItemDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.ItemService;
import com.inventorycontrolapi.services.exceptions.NotFoundItemException;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;

@ExtendWith(SpringExtension.class)
public class DeleteItemServiceTests {
	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaItemId() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);

		ItemModel itemModelMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMock));

		// Test
		DeleteItemDTORequest deleteItemDTORequest = new DeleteItemDTORequest("0", companyModelMock.getCompanyId().toString());

		DeleteItemDTOResponse deleteItemDTOResponse = this.itemService.delete(deleteItemDTORequest);

		Assertions.assertThat(deleteItemDTOResponse.getItemId()).isEqualTo(itemModelMock.getItemId().toString());
	}

	@Test
	public void retornaException_ItemNaoCadastrado() throws Exception {
		// Mock
		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

		// Test
		DeleteItemDTORequest deleteItemDTORequest = new DeleteItemDTORequest("0", "0");

		Assertions.assertThatExceptionOfType(NotFoundItemException.class)
			.isThrownBy(() -> this.itemService.delete(deleteItemDTORequest))
			.withMessage("Not found Item");
	}

	@Test
	public void retornaException_ItemEncontrado_MasDiferenteDoCompanyInformado() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);

		ItemModel itemModelMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMock));

		// Test
		DeleteItemDTORequest deleteItemDTORequest = new DeleteItemDTORequest("0", "1");

		Assertions.assertThatExceptionOfType(NotFoundItemException.class)
			.isThrownBy(() -> this.itemService.delete(deleteItemDTORequest))
			.withMessage("Not found Item");
	}
}
