package com.inventorycontrolapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTOResponse;
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
}