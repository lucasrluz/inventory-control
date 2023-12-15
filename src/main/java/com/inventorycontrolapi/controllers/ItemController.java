package com.inventorycontrolapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventorycontrolapi.domains.exceptions.InvalidItemDomainException;
import com.inventorycontrolapi.dtos.item.DeleteItemDTORequest;
import com.inventorycontrolapi.dtos.item.DeleteItemDTOResponse;
import com.inventorycontrolapi.dtos.item.GetAllItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetAllItemDTOResponse;
import com.inventorycontrolapi.dtos.item.GetItemDTORequest;
import com.inventorycontrolapi.dtos.item.GetItemDTOResponse;
import com.inventorycontrolapi.dtos.item.SaveItemDTORequest;
import com.inventorycontrolapi.dtos.item.SaveItemDTOResponse;
import com.inventorycontrolapi.services.ItemService;
import com.inventorycontrolapi.services.exceptions.NameAlreadyRegisteredException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemCategoryException;
import com.inventorycontrolapi.services.exceptions.NotFoundItemException;

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

	@GetMapping
	public ResponseEntity<Object> getAll(Authentication authentication) {
		String companyId = authentication.getName();

		GetAllItemDTORequest getAllItemDTORequest = new GetAllItemDTORequest(companyId);

		List<GetAllItemDTOResponse> getAllItemDTOResponses = this.itemService.getAll(getAllItemDTORequest);

		return ResponseEntity.status(200).body(getAllItemDTOResponses);
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<Object> get(@PathVariable String itemId, Authentication authentication) {
		try {
			String companyId = authentication.getName();

			GetItemDTORequest getItemDTORequest = new GetItemDTORequest(itemId, companyId);

			GetItemDTOResponse getItemDTOResponse = this.itemService.get(getItemDTORequest);

			return ResponseEntity.status(200).body(getItemDTOResponse);
		} catch (NotFoundItemException exception) {
			return ResponseEntity.status(404).body(exception.getMessage());
		}
	}

	@DeleteMapping("/{itemId}")
	public ResponseEntity<Object> delete(@PathVariable String itemId, Authentication authentication) {
		try {
			String companyId = authentication.getName();

			DeleteItemDTORequest deleteItemDTORequest = new DeleteItemDTORequest(itemId, companyId);

			DeleteItemDTOResponse deleteItemDTOResponse = this.itemService.delete(deleteItemDTORequest);

			return ResponseEntity.status(200).body(deleteItemDTOResponse);
		} catch (NotFoundItemException exception) {
			return ResponseEntity.status(404).body(exception.getMessage());
		}
	}
}
