package com.inventorycontrolapi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.inventorycontrolapi.dtos.company.SignUpCompanyDTORequest;
import com.inventorycontrolapi.dtos.company.SignUpCompanyDTOResponse;
import com.inventorycontrolapi.models.CompanyModel;
import com.inventorycontrolapi.repositories.CompanyRepository;
import com.inventorycontrolapi.services.exceptions.EmailAlreadyRegisteredException;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class CompanyService {
	private CompanyRepository companyRepository;

	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public SignUpCompanyDTOResponse signUp(SignUpCompanyDTORequest signUpCompanyDTORequest) throws EmailAlreadyRegisteredException {
		Optional<CompanyModel> findCompanyModelByEmail = this.companyRepository.findByEmail(
			signUpCompanyDTORequest.getEmail()
		);
		
		if (!findCompanyModelByEmail.isEmpty()) {
			throw new EmailAlreadyRegisteredException();
		}

		String hashPassword = BCrypt.withDefaults().hashToString(12, signUpCompanyDTORequest.getPassword().toCharArray());

		CompanyModel companyModel = new CompanyModel(
			signUpCompanyDTORequest.getName(),
			signUpCompanyDTORequest.getEmail(),
			hashPassword
		);

		CompanyModel saveCompanyModel = this.companyRepository.save(companyModel);

		return new SignUpCompanyDTOResponse(saveCompanyModel.getCompanyId().toString());
	}
}
