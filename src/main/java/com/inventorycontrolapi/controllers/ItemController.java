package com.inventorycontrolapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolapi.domains.exceptions.InvalidItemDomainException;
import com.inventorycontrolapi.dtos.item.SaveItemDTORequest;
import com.inventorycontrolapi.dtos.item.SaveItemDTOResponse;
import com.inventorycontrolapi.services.ItemService;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;

@RestController
@RequestMapping("/api/item")
public class ItemController {
	private ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody SaveItemDTORequest saveItemDTORequest, Authentication authentication) {
		try {
			String companyId = authentication.getName();
			
			saveItemDTORequest.setCompanyId(companyId);

			SaveItemDTOResponse saveItemDTOResponse = this.itemService.save(saveItemDTORequest);

			return ResponseEntity.status(201).body(saveItemDTOResponse);
		} catch (InvalidItemDomainException | NameAlreadyRegisteredException exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		} catch (NotFoundItemCategoryException exception) {
			return ResponseEntity.status(404).body(exception.getMessage());
		}
	}
}
