package com.inventorycontrolapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inventorycontrolapi.dtos.company.DeleteCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.DeleteCompanyDTOResponse;
import com.inventorycontrolapi.dtos.company.GetCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.GetCompanyDTOResponse;
import com.inventorycontrolapi.dtos.company.SignInCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignInCompanyDTOResponse;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTOResponse;
import com.inventorycontrolapi.dtos.company.UpdateCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.UpdateCompanyDTOResponse;
import com.inventorycontrolapi.services.CompanyService;

@RestController
@Validated
@RequestMapping("/api/company")
public class CompanyController extends ResponseEntityExceptionHandler {
	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}	

	@PostMapping("/auth/signup")
	public ResponseEntity<Object> signUp(@RequestBody SignUpCompanyDTORequest signUpCompanyDTORequest) {
		try {
			SignUpCompanyDTOResponse signUpCompanyDTOResponse = this.companyService.signUp(signUpCompanyDTORequest);

			return ResponseEntity.status(201).body(signUpCompanyDTOResponse);
		} catch (Exception exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@PostMapping("/auth/signin")
	public ResponseEntity<Object> signIn(@RequestBody SignInCompanyDTORequest signInCompanyDTORequest) {
		try {
			SignInCompanyDTOResponse signInCompanyDTOResponse = this.companyService.signIn(signInCompanyDTORequest);

			return ResponseEntity.status(201).body(signInCompanyDTOResponse);
		} catch (Exception exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<Object> get(Authentication authentication) {
		String companyId = authentication.getName();

		GetCompanyDTORequest getCompanyDTORequest = new GetCompanyDTORequest(companyId);

		GetCompanyDTOResponse getCompanyDTOResponse= this.companyService.get(getCompanyDTORequest);

		return ResponseEntity.status(200).body(getCompanyDTOResponse);
	}

	@PutMapping
	public ResponseEntity<Object> update(@RequestBody UpdateCompanyDTORequest updateCompanyDTORequest, Authentication authentication) {
		try {
			String companyId = authentication.getName();

			updateCompanyDTORequest.setCompanyId(companyId);

			UpdateCompanyDTOResponse updateCompanyDTOResponse = this.companyService.update(updateCompanyDTORequest);

			return ResponseEntity.status(201).body(updateCompanyDTOResponse);
		} catch (Exception exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<Object> delete(Authentication authentication) {
		String companyId = authentication.getName();

		DeleteCompanyDTORequest deleteCompanyDTORequest = new DeleteCompanyDTORequest(companyId);

		DeleteCompanyDTOResponse deleteCompanyDTOResponse = this.companyService.delete(deleteCompanyDTORequest);

		return ResponseEntity.status(200).body(deleteCompanyDTOResponse);
	}
}
