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

import com.inventorycontrolapi.dtos.itemCategory.UpdateItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.UpdateItemCategoryDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.services.ItemCategoryService;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;
import com.inventorycontrolapi.util.company.CompanyModelBuilder;
import com.inventorycontrolapi.util.itemCategory.ItemCategoryModelBuilder;
import com.inventorycontrolapi.util.itemCategory.UpdateItemCategoryDTORequestBuilder;

@ExtendWith(SpringExtension.class)
public class UpdateItemCategoryServiceTests {
	@InjectMocks
	private ItemCategoryService itemCategoryService;

	@Mock
	private CompanyRepository companyRepository;

	@Mock
	private ItemCategoryRepository itemCategoryRepository;

	@Test
	public void retornaInformacoesDoItemCategory() throws Exception {
		// Mocks
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemCategoryModelMock));

		BDDMockito.when(this.itemCategoryRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));

		itemCategoryModelMock.setName("Item Category B");

		BDDMockito.when(this.itemCategoryRepository.save(ArgumentMatchers.any())).thenReturn(itemCategoryModelMock);

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		UpdateItemCategoryDTOResponse updateItemCategoryDTOResponse = this.itemCategoryService.update(updateItemCategoryDTORequest);

		Assertions.assertThat(updateItemCategoryDTOResponse.getItemCategoryId())
			.isEqualTo(itemCategoryModelMock.getItemCategoryId().toString());
		Assertions.assertThat(updateItemCategoryDTOResponse.getName())
			.isEqualTo("Item Category B");
	}

	@Test
	public void retornaException_ItemCategoryNaoEncontrado_ItemCategoryNaoCadastrado() throws Exception {
		// Mocks
		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		Assertions.assertThatExceptionOfType(NotFoundItemCategoryException.class)
			.isThrownBy(() -> this.itemCategoryService.update(updateItemCategoryDTORequest))
			.withMessage("Not found Item Category");
	}

	@Test
	public void retornaException_ItemCategoryNaoEncontrado_CompanyDoItemCategoryDiferenteDoInformado() throws Exception {
		// Mocks
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemCategoryModelMock));
		
		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidDataAndInvalidCompanyId();

		Assertions.assertThatExceptionOfType(NotFoundItemCategoryException.class)
			.isThrownBy(() -> this.itemCategoryService.update(updateItemCategoryDTORequest))
			.withMessage("Not found Item Category");
	}
	
	@Test
	public void retornaException_NameJaUtilizado() throws Exception {
		// Mocks
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemCategoryModelMock));

		itemCategoryModelMock.setItemCategoryId(1L);
		itemCategoryModelMock.setName("Item Category B");
		BDDMockito.when(this.itemCategoryRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.of(itemCategoryModelMock));
		
		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();
		
		Assertions.assertThatExceptionOfType(NameAlreadyRegisteredException.class)
			.isThrownBy(() -> this.itemCategoryService.update(updateItemCategoryDTORequest))
			.withMessage("Name already registered");
	}

	@Test
	public void retornaInformacoesDoItemCategory_NameJaUtilizadoPorOutraCompany() throws Exception {
		// Mocks
		CompanyModel companyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		ItemCategoryModel itemCategoryModelMock = ItemCategoryModelBuilder.createWithItemCategoryId(companyModelMock);
		BDDMockito.when(this.itemCategoryRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(itemCategoryModelMock));

		CompanyModel newCompanyModelMock = CompanyModelBuilder.createWithCompanyIdAndHashPassword();
		newCompanyModelMock.setCompanyId(1L);
		newCompanyModelMock.setName("Company B");
		newCompanyModelMock.setEmail("companyb@gmail.com");

		ItemCategoryModel newItemCategoryModelMock = new ItemCategoryModel(
			1L,
			"Item Category B",
			newCompanyModelMock
		);

		BDDMockito.when(this.itemCategoryRepository.findByName(ArgumentMatchers.any())).thenReturn(Optional.of(newItemCategoryModelMock));

		BDDMockito.when(this.companyRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(companyModelMock));

		itemCategoryModelMock.setName("Item Category B");

		BDDMockito.when(this.itemCategoryRepository.save(ArgumentMatchers.any())).thenReturn(itemCategoryModelMock);

		// Test
		UpdateItemCategoryDTORequest updateItemCategoryDTORequest = UpdateItemCategoryDTORequestBuilder.createWithValidData();

		UpdateItemCategoryDTOResponse updateItemCategoryDTOResponse = this.itemCategoryService.update(updateItemCategoryDTORequest);

		Assertions.assertThat(updateItemCategoryDTOResponse.getItemCategoryId())
			.isEqualTo(itemCategoryModelMock.getItemCategoryId().toString());
		Assertions.assertThat(updateItemCategoryDTOResponse.getName())
			.isEqualTo("Item Category B");
	}
}
