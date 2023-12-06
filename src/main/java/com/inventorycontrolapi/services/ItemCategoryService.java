package com.inventorycontrolapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventorycontrolapi.domains.ItemCategoryDomain;
import com.inventorycontrolapi.domains.exceptions.InvalidItemCategoryDomainException;
import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTOResponse;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;

@Service
public class ItemCategoryService {
	private ItemCategoryRepository itemCategoryRepository;
	private CompanyRepository companyRepository;

	public ItemCategoryService(ItemCategoryRepository itemCategoryRepository, CompanyRepository companyRepository) {
		this.itemCategoryRepository = itemCategoryRepository;
		this.companyRepository = companyRepository;
	}

	public SaveItemCategoryDTOResponse save(SaveItemCategoryDTORequest saveItemCategoryDTORequest) throws InvalidItemCategoryDomainException {
		ItemCategoryDomain.validate(saveItemCategoryDTORequest.getName());

		Optional<CompanyModel> findCompanyModelByCompanyId = this.companyRepository.findById(
			Long.parseLong(saveItemCategoryDTORequest.getCompanyId())
		);

		ItemCategoryModel itemCategoryModel = new ItemCategoryModel(
			saveItemCategoryDTORequest.getName(),
			findCompanyModelByCompanyId.get()
		);

		ItemCategoryModel saveItemCategoryModel = this.itemCategoryRepository.save(itemCategoryModel);

		return new SaveItemCategoryDTOResponse(saveItemCategoryModel.getItemCategoryId().toString());
	}

	public List<GetAllItemCategoryDTOResponse> getAll(GetAllItemCategoryDTORequest getAllItemCategoryDTORequest) {
		Optional<CompanyModel> findCompanyModel = this.companyRepository.findById(Long.parseLong(
			getAllItemCategoryDTORequest.getCompanyId())
		);

		List<ItemCategoryModel> findAllItemCategoryModel = this.itemCategoryRepository.findByCompanyModel(
			findCompanyModel.get()	
		);	

		List<GetAllItemCategoryDTOResponse> getAllItemCategoryDTOResponses = new ArrayList<GetAllItemCategoryDTOResponse>();

		findAllItemCategoryModel.forEach(item -> {
			GetAllItemCategoryDTOResponse getAllItemCategoryDTOResponse = new GetAllItemCategoryDTOResponse(
				item.getItemCategoryId().toString(),
				item.getName()
			);

			getAllItemCategoryDTOResponses.add(getAllItemCategoryDTOResponse);
		});

		return getAllItemCategoryDTOResponses;
	}
}
