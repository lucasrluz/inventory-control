package com.inventorycontrolapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTORequest;
import com.inventorycontrolapi.dtos.itemCategory.SaveItemCategoryDTOResponse;
import com.inventorycontrolapi.services.ItemCategoryService;

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
}
