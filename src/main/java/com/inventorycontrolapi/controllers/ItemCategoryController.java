package com.inventorycontrolapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolapi.domains.exceptions.InvalidItemCategoryDomainException;
import com.inventorycontrolapi.dtos.itemCategory.DeleteItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.DeleteItemCategoryDTOResponse;
import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.GetAllItemCategoryDTOResponse;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTOResponse;
import com.inventorycontrolapi.dtos.itemCategory.UpdateItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.UpdateItemCategoryDTOResponse;
import com.inventorycontrolapi.services.ItemCategoryService;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;

@RestController
@RequestMapping("/api/item-category")
public class ItemCategoryController {
	private ItemCategoryService itemCategoryService;

	public ItemCategoryController(ItemCategoryService itemCategoryService) {
		this.itemCategoryService = itemCategoryService;
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody SaveItemCategoryDTORequest saveItemCategoryDTORequest, Authentication authentication) {
		try {
			String companyId = authentication.getName();

			saveItemCategoryDTORequest.setCompanyId(companyId);

			SaveItemCategoryDTOResponse saveItemCategoryDTOResponse = this.itemCategoryService.save(saveItemCategoryDTORequest);

			return ResponseEntity.status(201).body(saveItemCategoryDTOResponse);
		} catch (Exception exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<Object> getAll(Authentication authentication) {
		String companyId = authentication.getName();

		GetAllItemCategoryDTORequest getAllItemCategoryDTORequest = new GetAllItemCategoryDTORequest(companyId);

		List<GetAllItemCategoryDTOResponse> getAllItemCategoryDTOResponses = this.itemCategoryService.getAll(
			getAllItemCategoryDTORequest
		);

		return ResponseEntity.status(200).body(getAllItemCategoryDTOResponses);
	}

	@PutMapping("/{itemCategoryId}")
	public ResponseEntity<Object> update(@PathVariable String itemCategoryId, @RequestBody UpdateItemCategoryDTORequest updateItemCategoryDTORequest, Authentication authentication) {
		try {
			String companyId = authentication.getName();

			updateItemCategoryDTORequest.setItemCategoryId(itemCategoryId);
			updateItemCategoryDTORequest.setCompanyId(companyId);

			UpdateItemCategoryDTOResponse updateItemCategoryDTOResponse = this.itemCategoryService.update(
				updateItemCategoryDTORequest
			);

			return ResponseEntity.status(200).body(updateItemCategoryDTOResponse);
		} catch (NotFoundItemCategoryException exception) {
			return ResponseEntity.status(404).body(exception.getMessage());
		} catch (InvalidItemCategoryDomainException | NameAlreadyRegisteredException exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@DeleteMapping("/{itemCategoryId}")
	public ResponseEntity<Object> delete(@PathVariable String itemCategoryId, Authentication authentication) {
		try {
			String companyId = authentication.getName();
			DeleteItemCategoryDTORequest deleteItemCategoryDTORequest = new DeleteItemCategoryDTORequest(itemCategoryId, companyId);

			DeleteItemCategoryDTOResponse deleteItemCategoryDTOResponse = this.itemCategoryService.delete(deleteItemCategoryDTORequest);

			return ResponseEntity.status(200).body(deleteItemCategoryDTOResponse);
		} catch (Exception exception) {
			return ResponseEntity.status(404).body(exception.getMessage());
		}
	}

}
