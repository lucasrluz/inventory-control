package com.inventorycontrolapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventorycontrolapi.domains.ItemDomain;
import com.inventorycontrolapi.domains.exceptions.InvalidItemDomainException;
import com.inventorycontrolapi.dtos.item.GetAllItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetAllItemDTOResponse;
import com.inventorycontrolapi.dtos.item.SaveItemDTORequest;
import com.inventorycontrolapi.dtos.item.SaveItemDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;

@Service
public class ItemService {
	private ItemRepository itemRepository;
	private ItemCategoryRepository itemCategoryRepository;
	private CompanyRepository companyRepository;

	public ItemService(ItemRepository itemRepository, ItemCategoryRepository itemCategoryRepository, CompanyRepository companyRepository) {
		this.itemRepository = itemRepository;
		this.itemCategoryRepository = itemCategoryRepository;
		this.companyRepository = companyRepository;
	}

	public SaveItemDTOResponse save(SaveItemDTORequest saveItemDTORequest) throws InvalidItemDomainException, NotFoundItemCategoryException, NameAlreadyRegisteredException {
		ItemDomain.validate(saveItemDTORequest.getName());

		Optional<ItemCategoryModel> findItemCategoryByItemCategoryId = this.itemCategoryRepository.findById(
			Long.parseLong(saveItemDTORequest.getItemCategoryId())
		);

		if (findItemCategoryByItemCategoryId.isEmpty()) {
			throw new NotFoundItemCategoryException();
		}

		Optional<ItemModel> findItemByName= this.itemRepository.findByName(saveItemDTORequest.getName());

		if (!findItemByName.isEmpty() && findItemByName.get().getCompanyModel().getCompanyId().toString().equals(saveItemDTORequest.getCompanyId())) {
			throw new NameAlreadyRegisteredException();
		}

		Optional<CompanyModel> findCompanyModelByCompanyId = this.companyRepository.findById(Long.parseLong(saveItemDTORequest.getCompanyId()));

		ItemModel itemModel = new ItemModel(
			saveItemDTORequest.getName(),
			Double.parseDouble(saveItemDTORequest.getUnitPrice()),
			Integer.parseInt(saveItemDTORequest.getQuantityInStock()),
			findCompanyModelByCompanyId.get(),
			findItemCategoryByItemCategoryId.get()
		);

		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		return new SaveItemDTOResponse(saveItemModel.getItemId().toString());
	}

	public List<GetAllItemDTOResponse> getAll(GetAllItemDTORequest getAllItemDTORequest) {
		Optional<CompanyModel> findCompanyModelById = this.companyRepository.findById(
			Long.parseLong(getAllItemDTORequest.getCompanyId())
		);

		List<ItemModel> itemModels = this.itemRepository.findByCompanyModel(findCompanyModelById.get());

		List<GetAllItemDTOResponse> getAllItemDTOResponses = new ArrayList<GetAllItemDTOResponse>();

		itemModels.forEach(element -> {
			GetAllItemDTOResponse getAllItemDTOResponse = new GetAllItemDTOResponse(
				element.getItemId().toString(),
				element.getName(),
				String.valueOf(element.getUnitPrice()),
				String.valueOf(element.getQuantityInStock()),
				element.getItemCategoryModel().getName()
			);

			getAllItemDTOResponses.add(getAllItemDTOResponse);
		});

		return getAllItemDTOResponses;
	}
}
