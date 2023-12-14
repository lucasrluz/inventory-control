package com.inventorycontrolapi.unit.services.item;

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

import com.inventorycontrolapi.dtos.item.GetAllItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetAllItemDTOResponse;
import com.inventorycontrolapi.dtos.item.GetItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetItemDTOResponse;
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
public class GetItemServiceTests {
	@InjectMocks
	private ItemService itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void retornaInformacoesDoItem() throws Exception {
		// Mock
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);

		ItemModel itemModelMock = new ItemModel(0L, "Item A", 1.11, 1, companyModelMock, itemCategoryModelMock);

		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemModelMock));

		// Test
		GetItemDTORequest getItemDTORequest = new GetItemDTORequest("0", companyModelMock.getCompanyId().toString());

		GetItemDTOResponse getItemDTOResponse = this.itemService.get(getItemDTORequest);

		Assertions.assertThat(getItemDTOResponse.getItemId()).isEqualTo(itemModelMock.getItemId().toString());
		Assertions.assertThat(getItemDTOResponse.getName()).isEqualTo(itemModelMock.getName());
		Assertions.assertThat(getItemDTOResponse.getUnitPrice()).isEqualTo(String.valueOf(itemModelMock.getUnitPrice()));
		Assertions.assertThat(getItemDTOResponse.getQuantityInStock()).isEqualTo(String.valueOf(itemModelMock.getQuantityInStock()));
		Assertions.assertThat(getItemDTOResponse.getItemCategory()).isEqualTo(itemModelMock.getItemCategoryModel().getName());
	}

	@Test
	public void retornaException_ItemNaoCadastrado() throws Exception {
		// Mock
		BDDMockito.when(this.itemRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

		// Test
		GetItemDTORequest getItemDTORequest = new GetItemDTORequest("0", "0");

		Assertions.assertThatExceptionOfType(NotFoundItemException.class)
			.isThrownBy(() -> this.itemService.get(getItemDTORequest))
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
		GetItemDTORequest getItemDTORequest = new GetItemDTORequest("0", "1");

		Assertions.assertThatExceptionOfType(NotFoundItemException.class)
			.isThrownBy(() -> this.itemService.get(getItemDTORequest))
			.withMessage("Not found Item");
	}
}
