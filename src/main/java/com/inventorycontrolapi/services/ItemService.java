package com.inventorycontrolapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventorycontrolapi.domains.ItemDomain;
import com.inventorycontrolapi.domains.exceptions.InvalidItemDomainException;
import com.inventorycontrolapi.dtos.item.DeleteItemDTORequest;
import com.inventorycontrolapi.dtos.item.DeleteItemDTOResponse;
import com.inventorycontrolapi.dtos.item.GetAllItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetAllItemDTOResponse;
import com.inventorycontrolapi.dtos.item.GetItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetItemDTOResponse;
import com.inventorycontrolapi.dtos.item.SaveItemDTORequest;
import com.inventorycontrolapi.dtos.item.SaveItemDTOResponse;
import com.inventorycontrolapi.dtos.item.UpdateItemDTORequest;
import com.inventorycontrolapi.dtos.item.UpdateItemDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.models.ItemCategoryModel;
import com.inventorycontrolapi.models.ItemModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.repositories.ItemCategoryRepository;
import com.inventorycontrolapi.repositories.ItemRepository;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemException;

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

	public GetItemDTOResponse get(GetItemDTORequest getItemDTORequest) throws NotFoundItemException {
		Optional<ItemModel> findItemModelById = this.itemRepository.findById(
			Long.parseLong(getItemDTORequest.getItemId())
		);

		if (findItemModelById.isEmpty()) {
			throw new NotFoundItemException();
		}

		if (!findItemModelById.get().getCompanyModel().getCompanyId().toString().equals(getItemDTORequest.getCompanyId())) {
			throw new NotFoundItemException();
		}

		return new GetItemDTOResponse(
			findItemModelById.get().getItemId().toString(),
			findItemModelById.get().getName(),
			String.valueOf(findItemModelById.get().getUnitPrice()),
			String.valueOf(findItemModelById.get().getQuantityInStock()),
			findItemModelById.get().getItemCategoryModel().getName()
		);
	}

	public UpdateItemDTOResponse update(UpdateItemDTORequest updateItemDTORequest) throws NotFoundItemException, InvalidItemDomainException, NameAlreadyRegisteredException, NotFoundItemCategoryException {
		Optional<ItemModel> findItemModelById = this.itemRepository.findById(
			Long.parseLong(updateItemDTORequest.getItemId())
		);

		if (findItemModelById.isEmpty()) {
			throw new NotFoundItemException();
		}

		if (!findItemModelById.get().getCompanyModel().getCompanyId().toString().equals(updateItemDTORequest.getCompanyId())) {
			throw new NotFoundItemException();
		}

		ItemDomain.validate(updateItemDTORequest.getName());

		Optional<ItemModel> findItemByName = this.itemRepository.findByName(updateItemDTORequest.getName());

		if (!findItemByName.isEmpty() && findItemByName.get().getCompanyModel().getCompanyId().toString().equals(updateItemDTORequest.getCompanyId())) {
			throw new NameAlreadyRegisteredException();
		}

		Optional<ItemCategoryModel> findItemCategoryByItemCategoryId = this.itemCategoryRepository.findById(
			Long.parseLong(updateItemDTORequest.getItemCategoryId())
		);

		if (findItemCategoryByItemCategoryId.isEmpty()) {
			throw new NotFoundItemCategoryException();
		}

		Optional<CompanyModel> findCompanyModelById = this.companyRepository.findById(
			Long.parseLong(updateItemDTORequest.getItemId())
		);

		ItemModel itemModel = new ItemModel(
			Long.parseLong(updateItemDTORequest.getItemId()),
			updateItemDTORequest.getName(),
			Double.parseDouble(updateItemDTORequest.getUnitPrice()),
			Integer.parseInt(updateItemDTORequest.getQuantityInStock()),
			findCompanyModelById.get(),
			findItemCategoryByItemCategoryId.get()
		);

		ItemModel saveItemModel = this.itemRepository.save(itemModel);

		return new UpdateItemDTOResponse(saveItemModel.getItemId().toString());
	}

	public DeleteItemDTOResponse delete(DeleteItemDTORequest deleteItemDTORequest) throws NotFoundItemException {
		Optional<ItemModel> findItemModelById = this.itemRepository.findById(
			Long.parseLong(deleteItemDTORequest.getItemId())
		);

		if (findItemModelById.isEmpty()) {
			throw new NotFoundItemException();
		}

		if (!findItemModelById.get().getCompanyModel().getCompanyId().toString().equals(deleteItemDTORequest.getCompanyId())) {
			throw new NotFoundItemException();
		}

		this.itemRepository.deleteById(Long.parseLong(deleteItemDTORequest.getItemId()));

		return new DeleteItemDTOResponse(deleteItemDTORequest.getItemId());
	}
}
